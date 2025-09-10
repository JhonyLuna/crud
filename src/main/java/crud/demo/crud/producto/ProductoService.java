package crud.demo.crud.producto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    // Crear
    public Producto crear(Producto p) {
        p.setId(null); // ignoramos el id que viene del cliente porque lo genera la bd aunque
        String nombre = p.getNombre() == null ? null : p.getNombre().trim();
        if (nombre != null && repo.existsByNombreIgnoreCase(nombre)) {
            // 409 Conflict, sin clase custom
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre '" + nombre + "' ya está en uso");
        }
        p.setNombre(nombre);
        return repo.save(p);
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    // Obtener por id (404 si no existe)
    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    // Actualizar
    public Producto actualizar(Long id, Producto p) {
        p.setId(id);
        return repo.save(p);
    }

    // Eliminar
    public void eliminar(Long id) {
        Producto actual = buscarPorId(id);
        repo.delete(actual);
    }
}
