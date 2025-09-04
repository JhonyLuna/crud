package crud.demo.crud.producto;

public class ProductoConsultaDto {

    private String nombre;

    public ProductoConsultaDto() {
    }

    public ProductoConsultaDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    void setNombre(String nombre){
        this.nombre = nombre;
    }
}
