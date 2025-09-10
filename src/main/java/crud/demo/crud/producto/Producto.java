package crud.demo.crud.producto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "productos")
public class Producto {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id; // Atributo id

   
     @NotBlank(message = "El nombre no debe estar vacio")
     private String nombre;

     @NotBlank(message = "El nombre no debe estar vacio")
     private String descripcion;
    
     @NotNull(message  = "El campo es obligatorio")
     @Positive(message = "El precio debe ser mayor que cero")
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }   
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

}

