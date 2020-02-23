/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTenda;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Hemihundias
 */
@Entity
@Table(name="horas")
public class Horas implements Serializable{
    @Id
    @Column(name="id_tienda")
    private int id_tienda;
    @Id
    @Column(name="id_empleado")
    private int id_empleado;
    @Column(name="horas")
    private int horas;

    /*@ManyToOne
    @JoinColumn(name="id")
    private Tiendas tiendas;
    
    @ManyToOne
    @JoinColumn(name="id")
    private Empleados empleados;*/
    
    public Horas() {
    }

    public Horas(int id_tienda, int id_empleado, int horas) {
        this.id_tienda = id_tienda;
        this.id_empleado = id_empleado;
        this.horas = horas;
    }

    public int getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(int id_tienda) {
        this.id_tienda = id_tienda;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    
}
