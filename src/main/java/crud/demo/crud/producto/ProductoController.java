package crud.demo.crud.producto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {


// hacemos inyeccion de dependencia del service por emdio de constructor 
    private final ProductoService service; 
    public ProductoController(ProductoService service) {
        this.service = service;
    }


    // POST /api/productos         -> crear
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto body) {
        Producto productoCreado = service.crear(body);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }   

    // GET /api/productos/{id}     -> obtener por id
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    
    // GET /api/productos?q=texto  -> obtener todos los productos (filtrado opcional)
     @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    // PUT /api/productos/{id}     -> actualizar
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto body) {
        return service.actualizar(id, body);
    }

    // DELETE /api/productos/{id}  -> eliminar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
