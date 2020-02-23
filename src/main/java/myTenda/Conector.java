/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTenda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Hemihundias
 */
public class Conector {
    private List<Productos> productos = new ArrayList();
    private List<Tiendas> tiendas = new ArrayList();
    
    private int in,ide,i,id_provincia;
    private String nombre,apellidos, email,descripcion,provincia,ciudad;
    private Double precio;
    private boolean x = true;
    private File datos;
    
    Scanner teclado = new Scanner(System.in);
        
    //Mediante este método sacamos la información de las provincias contenida en el json, la pasamos a la clase Provincias, y posteriormente a la bd
    public void cargarProvincias() {
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT p FROM Provincias p");
        List<Provincias> provin = q1.getResultList();
        
        if(provin.isEmpty()){
            datos = new File("provincias.json");
            if(datos.exists()){
                try{     
                    FileReader fluxoDatos = new FileReader(datos);                
                    BufferedReader buferEntrada = new BufferedReader(fluxoDatos);

                    StringBuilder jsonBuilder = new StringBuilder();
                    String linea;

                    while ((linea=buferEntrada.readLine()) != null) {
                        jsonBuilder.append(linea).append("\n");                    
                    }
                    String json = jsonBuilder.toString();

                    Gson gson = new Gson(); 

                    Provincias[] provincias = gson.fromJson(json, Provincias[].class);  

                    for(Provincias prov:provincias){
                        Transaction tran = null;
                        try{
                            session = HibernateUtil.getSessionFactory().openSession();
                            tran = session.beginTransaction();

                            session.save(prov);

                            tran.commit();
                        }catch(HibernateException e){
                            System.err.println(e.getMessage());
                        }                    
                    }

                }catch (JsonSyntaxException | IOException ex){
                    Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                System.out.println("Imposible actualizar datos de las provincias.");
            }         
        }
        
    }     
           
    //Método generico para insertar en la base de datos nuestros objetos
    public void insertarDatos(Object ob){
        Transaction tran = null;
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            tran = session.beginTransaction();
            
            session.save(ob);
            
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        }
    }
    
    //Método para la creación de una nueva Tienda en la bd
    public void insertarTienda(){                
        System.out.println("Teclee nombre de la nueva tienda:");
        nombre = teclado.next();
        
        mostrarProvincias();
        
        System.out.println("Teclee el id de la provincia de emplazamiento de la tienda:");                    
        
        provincia = teclado.next();            
        id_provincia = Integer.parseInt(provincia);
        
        System.out.println("Teclee la ciudad de emplazamiento de la tienda:");
        ciudad = teclado.next();   
        
        Tiendas tienda = new Tiendas(nombre,id_provincia,ciudad);
        insertarDatos(tienda);
    }
    
    //Método para dar de alta un nuevo producto
    public void insertarProducto(){                        
        System.out.println("Teclee nombre del nuevo producto:");
        nombre = teclado.nextLine();
        
        System.out.println("Teclee una descripción del producto:");
        descripcion = teclado.nextLine();
                            
        System.out.println("Teclee el precio del producto:");
        while(!teclado.hasNextDouble()){
            System.out.println("Valor incorrecto, intentelo de nuevo.");
        }
        precio = teclado.nextDouble();        
        
        Productos producto = new Productos(nombre,descripcion,precio);
        insertarDatos(producto);
        
        teclado.nextLine();
    }
    
    //Método para dar de alta un producto en una tienda
    public void insertarProductoTienda(){             
        mostrarTiendas();
        System.out.println("Introduzca el número id de la tienda a la que se añadira el producto.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
                    
        mostrarProductos();
        System.out.println("Introduzca el número id del producto que quiere añadir a la tienda.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        in = teclado.nextInt();
        
        System.out.println("Introduzca el stock del producto.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        i = teclado.nextInt();
        
        Existencias existencia = new Existencias(ide,in,i);
        insertarDatos(existencia);
                
    }
    
    //Método que nos permite dar de alta un nuevo empleado
    public void insertarEmpleado(){                                
        System.out.println("Teclee nombre del nuevo empleado:");
        nombre = teclado.nextLine();
        
        System.out.println("Teclee apellidos:");
        apellidos = teclado.nextLine();                           
        
        Empleados empleado = new Empleados(nombre,apellidos);
        insertarDatos(empleado);
        
        teclado.nextLine();
    }
    
    //Método para dar de alta un nuevo cliente
    public void insertarCliente(){                                
        System.out.println("Teclee nombre del nuevo cliente:");
        nombre = teclado.nextLine();
        
        System.out.println("Teclee apellidos:");
        apellidos = teclado.nextLine();    
        
        System.out.println("Teclee mail:");
        email = teclado.nextLine(); 
          
        Clientes cliente = new Clientes(nombre,apellidos,email);
        insertarDatos(cliente);
    }
        
    //Método para registrar las horas trabajadas por cada empleado, y en que tienda.
    public void registrarHoras(){
        mostrarTiendas();

        System.out.println("Introduzca el id de la tienda en la que se han hecho esas horas.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        mostrarEmpleados();
                    
        System.out.println("Introduzca el id del empleado al que quiere imputar horas.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        in = teclado.nextInt();     

        System.out.println("Introduzca las horas a imputar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        i = teclado.nextInt();     
                
        Horas horas = new Horas(ide,in,i);
        insertarDatos(horas);
    }
    
    //Método que nos devuelve todas las provincias
    public void mostrarProvincias(){
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT p FROM Provincias p");
        List<Provincias> provincias = q1.getResultList();
        for(Provincias prov:provincias){
            System.out.printf("%-20s%-20s\n",prov.getId(),prov.getNombre());
        }        
    }
    
    //Método que nos devuelve todas las tiendas dadas de alta en la bd
    public void mostrarTiendas(){
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT t FROM Tiendas t");
        tiendas = q1.getResultList();
        for(Tiendas tien:tiendas){
            System.out.printf("%-20s%-20s%-20s\n",tien.getId(),tien.getNombre(),tien.getCiudad());
        }       
    }
    
    //Método que nos devuelve todos los productos dados de alta
    public void mostrarProductos(){
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT p FROM Productos p");
        productos = q1.getResultList();
        for(Productos prod:productos){
            System.out.printf("%-20s%-20s%-20s%-20s\n",prod.getId(),prod.getNombre(),prod.getDescripcion(),prod.getPrecio());
        }
    }
    
    //Método que nos devuelve la cantidad de cada producto que posee una tienda en concreto
    public void mostrarExistenciasTienda(){
        mostrarTiendas();
        
        System.out.println("Introduzca el id de la tienda de la que quiere consultar el stock.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT e FROM Existencias e WHERE e.id_tienda =" + ide);
        List<Existencias> existencias = q1.getResultList();
        for(Existencias ex:existencias){
            System.out.printf("%-20s%-20s%-20s\n",ex.getId_tienda(),ex.getId_producto(),ex.getStock());
        }      
    }
    
    //Método que nos devuelve todos los empleados dados de alta
    public void mostrarEmpleados(){
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT p FROM Productos p");
        List<Empleados> empleados = q1.getResultList();
        for(Empleados emp:empleados){
            System.out.printf("%-20s%-20s%-20s\n",emp.getId(),emp.getNombre(),emp.getApellidos());
        } 
    }
    
    //Método para mostrar los clientes dados de alta
    public void mostrarClientes(){
        Session session = HibernateUtil.getSessionFactory().openSession();
    
        Query q1 = session.createQuery("SELECT c FROM Clientes c");
        List<Clientes> clientes = q1.getResultList();
        for(Clientes cli:clientes){
            System.out.printf("%-20s%-20s%-20s%-20s\n",cli.getId(),cli.getNombre(),cli.getApellidos(),cli.getMail());
        } 
    }
    
    //Método para consultar el stock de una tienda en concreto
    public void consultarStock(){
        mostrarTiendas();
        System.out.println("Introduzca el id de la tienda de la que quiere consultar el stock.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Query q1 = session.createQuery("SELECT e FROM Existencias e WHERE e.id_tienda=" + ide);
        List<Existencias> existencias = q1.getResultList();
        for(Existencias ex:existencias){
            System.out.printf("%-20s%-20s%-20s\n",ex.getId_tienda(),ex.getId_producto(),ex.getStock());
        }
    }
    
    //Método que nos permite ver que productos hay en una tienda
    public void consultarProductosTienda(){
        System.out.println("Introduzca el id de la tienda de la que quiere consultar los productos.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
                
        Query q1 = session.createQuery("SELECT e FROM Existencias e WHERE e.id_tienda =" + ide);
        List<Existencias> existencias = q1.getResultList();
        for(Existencias ex:existencias){
            System.out.printf("%-20s\n",ex.getId_producto());
        }
    }    
    
    //Método que nos permite eliminar una tienda
    public void eliminarTienda(){
        mostrarTiendas();
        System.out.println("Teclee el número id de la tienda que quiere eliminar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        Transaction tran = null;
        try{            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            tran = session.beginTransaction();
            
            Query q1 = session.createQuery("DELETE FROM Tiendas WHERE id =" + ide);
            q1.executeUpdate();
                        
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        }        
    }
            
    //Método para la eliminación de un cliente
    public void eliminarCliente(){
        mostrarClientes();
        System.out.println("Teclee el número id del cliente que quiere eliminar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        Transaction tran = null;
        try{            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            tran = session.beginTransaction();
            
            Query q1 = session.createQuery("DELETE FROM Clientes WHERE id =" + ide);
            q1.executeUpdate();
                        
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        }                    
    }
    
    //Método que nos permite eliminar un empleado
    public void eliminarEmpleado(){
        mostrarEmpleados();
        System.out.println("Teclee el número id del empleado que quiere eliminar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();
        
        Transaction tran = null;
        try{            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            tran = session.beginTransaction();
            
            Query q1 = session.createQuery("DELETE FROM Empleados WHERE id =" + ide);
            q1.executeUpdate();
                        
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        }                       
    }        
    
    //Método para la eliminación de un producto, siempre y cuando su stock sea 0
    public void eliminarProducto(){
        mostrarProductos();
        System.out.println("Introduzca el id del producto que quiere eliminar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        in = teclado.nextInt();

        Transaction tran = null;
        try{            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            tran = session.beginTransaction();
            
            Query q1 = session.createQuery("DELETE FROM Productos WHERE id =" + in);
            q1.executeUpdate();
                        
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        } 
    }
    
    //Método que nos permite eliminar un producto de una tienda en concreto, siempre que su stock sea 0
    public void eliminarProductoTienda(){
        mostrarExistenciasTienda();
        System.out.println("Introduzca el id del producto que quiere eliminar.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();

        Transaction tran = null;
        try{            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            tran = session.beginTransaction();
            
            Query q1 = session.createQuery("DELETE FROM Existencias WHERE id_producto =" + ide);
            q1.executeUpdate();
                        
            tran.commit();
        }catch(HibernateException e){
            System.err.println(e.getMessage());
        }      
    }
    
    //Método que nos permite moidificar el stock de un producto en una tienda
    public void actualizarStock(){
        consultarStock();                
        
        System.out.println("Introduzca el id del producto del que quiere actualizar stock.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        ide = teclado.nextInt();

        System.out.println("Introduzca el nuevo stock del producto.");
        while(!teclado.hasNextInt()){
            System.out.println("Valor incorrecto, vuelva a intentarlo.");
        }
        in = teclado.nextInt();
        
        Session session = HibernateUtil.getSessionFactory().openSession();        
        Existencias existencia = (Existencias)session.get(Existencias.class, ide);
        
        existencia.setStock(in);
        
        session.update(existencia);             
    }
    
    //Método que nos genera un archivo json con las existencias de los productos
    public void informeStock(){
        try (FileWriter writer = new FileWriter("stock.json")){
            Session session = HibernateUtil.getSessionFactory().openSession();
                
            Query q1 = session.createQuery("SELECT e FROM Existencias e");
            List<Existencias> existencias = q1.getResultList();
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        
            gson.toJson(existencias, writer);
        } catch (IOException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
            
    //Método que saca por pantalla los titulares de "El País"
    public void leerTitulares(){
        XMLReader procesadorXML = null;
        try {

            //Creamos un parseador de texto e engadimoslle a nosa clase que vai parsear o texto
            procesadorXML = XMLReaderFactory.createXMLReader();
            TitularesXML titularesXML = new TitularesXML();
            procesadorXML.setContentHandler(titularesXML);

            //Indicamos o texto donde estan gardadas as persoas
            InputSource arquivo = new InputSource("http://ep00.epimg.net/rss/elpais/portada.xml");
            procesadorXML.parse(arquivo);

            //Imprimimos os datos lidos no XML
            ArrayList<Titular> titulares = titularesXML.getTitulares();
            for(i=0;i<titulares.size();i++){
                Titular tituloAux = titulares.get(i);
                System.out.println("Titular: " + tituloAux.getTitular());
            }

        } catch (SAXException | IOException e) {
            System.out.println("Ocurriu un erro ao ler o arquivo XML");
        }
        System.out.println("\nPresione cualquier tecla para salir de la sección noticias.");
        teclado.next();
    }
}    
