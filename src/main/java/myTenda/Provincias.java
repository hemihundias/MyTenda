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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hemihundias
 */
@Entity
@Table(name="provincias")
public class Provincias implements Serializable{
    @Column(name="nombre")
    private String nombre;
    @Id
    @Column(name="id")
    private int id;

    /*@OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="id_provincia")    
    private List<Tiendas> tiendas;*/
        
    public void Provincias(){
        
    }
    
    public Provincias() {
        this.id = 0;
        this.nombre = "";
    }

    public Provincias(String nome, int id) {
        this.nombre = nome;
        this.id = id;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nome) {
        this.nombre = nome;
    }
    
    
}