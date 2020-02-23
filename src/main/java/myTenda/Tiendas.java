/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTenda;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hemihundias
 */
@Entity
@Table(name="tiendas")
public class Tiendas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="id_provincia")
    private int id_provincia;
    @Column(name="ciudad")
    private String ciudad;    

    /*@OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="id_tienda")    
    private List<Horas> horas;
    
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="id_tienda")    
    private List<Existencias> existencias;
    
    @ManyToOne
    @JoinColumn(name="id")
    private Provincias provincia;*/

    public Tiendas() {
    }

    public Tiendas(String nombre, int id_provincia, String ciudad) {
        
        this.nombre = nombre;
        this.id_provincia = id_provincia;
        this.ciudad = ciudad;
    }

    public int getId() {
        return id;
    }
        
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
   
    
}
