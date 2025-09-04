package crud.demo.crud.producto;


import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<ProductoConsultaDto> listar(String q) {
    List<Producto> productos;
    
    if (q == null || q.isBlank()) {
        productos = repo.findAll();
    } else {
        productos = repo.findByNombreContainingIgnoreCase(q.trim());
    }

    // Convertir entidad -> DTO
    return productos.stream()
            .map(p -> new ProductoConsultaDto(p.getNombre()))
            .toList();
}

    // Crear
    public Producto crear(Producto p) {
        p.setId(null); //ignoramos el id que viene del cliente porque lo genera la bd aunque
        return repo.save(p);
    }

    // Obtener por id (404 si no existe)
    public Producto buscarPorId(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("No existe el producto " + id));
    }

    // Actualizar
    public Producto actualizar(Long id, Producto datos) {
        Producto actual = buscarPorId(id); // valida existencia
        actual.setNombre(datos.getNombre());
        actual.setPrecio(datos.getPrecio());
        return repo.save(actual);
    }

    // Eliminar
    public void eliminar(Long id) {
        Producto actual = buscarPorId(id);
        repo.delete(actual);
    }
}
