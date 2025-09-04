package crud.demo.crud.producto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id; // Atributo id

    @Column(nullable = false) // No nulo
     private String nombre;

     @Column(nullable = false) 
     private String descripcion;

    @Column(nullable = false)
    private Double precio;

    // Constructor vacío
    protected Producto() {}

    public Producto(String nombre, String descripcion, Double precio) { 
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId () {
        return id;
    }
    public void setId (Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }   
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

}

