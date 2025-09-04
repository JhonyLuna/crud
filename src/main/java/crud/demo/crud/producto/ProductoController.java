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
    public ResponseEntity<ProductoResponsDto> crear(@Valid @RequestBody ProductoDto body) {
        Producto nuevoProducto = new Producto(body.getNombre(), body.getDescripcion(),body.getPrecio() );
        Producto productoCreado = service.crear(nuevoProducto);

        ProductoResponsDto responsDto = new ProductoResponsDto();
        responsDto.setMensaje("producto creado con exito");
        responsDto.setNombre(productoCreado.getNombre());
        
        return new ResponseEntity<>(responsDto, HttpStatus.CREATED);
    }   

    // GET /api/productos/{id}     -> obtener por id
    @GetMapping("/{id}")
    public ProductoConsultaDto obtener(@PathVariable Long id) {
        Producto buscarProducto = service.buscarPorId(id);
        return new ProductoConsultaDto(buscarProducto.getNombre());
    }

    
    // GET /api/productos?q=texto  -> obtener todos los productos (filtrado opcional)
     @GetMapping
    public List<ProductoConsultaDto> listar(@RequestParam(value = "q", required = false) String q) {
        return service.listar(q);
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
