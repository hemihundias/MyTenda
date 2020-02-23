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
@Table(name="existencias")
public class Existencias implements Serializable{
    @Id
    @Column(name="id_tienda")
    private int id_tienda;
    @Id
    @Column(name="id_producto")
    private int id_producto;
    @Column(name="stock")
    private int stock;

    /*@ManyToOne
    @JoinColumn(name="id")
    private Tiendas tienda;
    
    @ManyToOne
    @JoinColumn(name="id")
    private Productos producto;*/
    
    public Existencias() {
    }

    public Existencias(int id_tienda, int id_producto, int stock) {
        this.id_tienda = id_tienda;
        this.id_producto = id_producto;
        this.stock = stock;
    }

    public int getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(int id_tienda) {
        this.id_tienda = id_tienda;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
}
