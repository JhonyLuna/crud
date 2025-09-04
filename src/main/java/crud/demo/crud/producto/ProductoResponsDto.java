package crud.demo.crud.producto;

public class ProductoResponsDto {


    private String mensaje;
    private String nombre;

    public ProductoResponsDto(){
    }

    public ProductoResponsDto(String nombre, String mensaje) {
     
        this.nombre = nombre;
        this.mensaje = mensaje;
    
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

}
