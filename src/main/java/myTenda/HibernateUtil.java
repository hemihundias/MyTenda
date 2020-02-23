package myTenda;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    //Este método devolve a sesión para poder facer operacións coa base de datos
    public static SessionFactory getSessionFactory(){
        
        //Se a sesion non se configurou, creamolo
        if(sessionFactory == null){
            try{
                Gson gson = new Gson();
                
                Config config = gson.fromJson(new FileReader("config.json"), Config.class);
                System.out.println(config.getHibernate().getDialect());
                
                Configuration conf = new Configuration();
                
                //Engadimos as propiedades
                Properties settings = new Properties();
                
                //Indicamos o conector da base de datos que vamos a usar
                settings.put(Environment.DRIVER,config.getHibernate().getDriver());
                
                //Indicamos a localización da base de datos que vamos a utilizar
                settings.put(Environment.URL,"jdbc:mysql://" + config.getDbConnection().getAddress() + "/" + config.getDbConnection().getName());
                
                //Indicamos o usuario da base de datos con cal nos vamos conectar e o seu contrasinal
                settings.put(Environment.USER,config.getDbConnection().getUser());
                settings.put(Environment.PASS,config.getDbConnection().getPassword());
                
                //Indicamos o dialecto que ten que usar Hibernate 
                settings.put(Environment.DIALECT,config.getHibernate().getDialect());
                
                //Indicamos que se as táboas todas se borren e se volvan crear
                settings.put(Environment.HBM2DDL_AUTO, config.getHibernate().getHBM2DDL_AUTO());
                
                //Indicamos que se mostre as operacións SQL que Hibernate leva a cabo
                settings.put(Environment.SHOW_SQL, config.getHibernate().getSHOW_SQL());
                conf.setProperties(settings);
                
                //Engaidmos aquelas clases nas que queremos facer persistencia
                conf.addAnnotatedClass(Provincias.class);
                conf.addAnnotatedClass(Tiendas.class);
                conf.addAnnotatedClass(Productos.class);
                conf.addAnnotatedClass(Existencias.class);
                conf.addAnnotatedClass(Empleados.class);
                conf.addAnnotatedClass(Clientes.class);
                conf.addAnnotatedClass(Horas.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sessionFactory = conf.buildSessionFactory(serviceRegistry);
            }catch(HibernateException e){
                e.printStackTrace();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sessionFactory;
    }
    
}