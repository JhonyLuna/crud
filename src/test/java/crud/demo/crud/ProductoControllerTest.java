package crud.demo.crud;

import crud.demo.crud.producto.GlobalExceptionHandler;
import crud.demo.crud.producto.Producto;
import crud.demo.crud.producto.ProductoController;
import crud.demo.crud.producto.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//anotaciones de clase
@WebMvcTest(controllers = ProductoController.class) // con este slice o capa web llamamos al controlador real
@Import(GlobalExceptionHandler.class) // asegura que el advice se aplique en el slice web
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc; // para hacer requests simuladas al controlador

    @MockBean
    private ProductoService service; // mock del servicio para controlar su comportamiento en tests

    @Test
    @DisplayName("Happy path: POST válido -> 201 y regresa el producto creado")
    void crearProducto_deberiaRetornar201_conBody() throws Exception {
        // Arrange (mock del service para que devuelva un producto “creado”)
        Producto creado = new Producto("computadora", "desc válida", 1200.0);
        creado.setId(1L);
        when(service.crear(ArgumentMatchers.any(Producto.class))).thenReturn(creado);

        String jsonRequest = """
            {
              "nombre": "computadora",
              "descripcion": "desc válida",
              "precio": 1200
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            // Ajusta estos asserts al body real que regresa tu controlador
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("computadora"))
            .andExpect(jsonPath("$.descripcion").value("desc válida"))
            .andExpect(jsonPath("$.precio").value(1200.0));
    }

    @Test
    @DisplayName("Unhappy: falta 'descripcion' (campo ausente) -> 400 con 'es obligatorio'")
    void crearProducto_400_faltaDescripcion() throws Exception {
        String jsonRequest = """
            {
              "nombre": "carro",
              "precio": 10
            }
            """;

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.mensaje.status").value(400))
            .andExpect(jsonPath("$.mensaje.error").value("Bad Request"))
            .andExpect(jsonPath("$.fields.descripcion").value("El campo 'descripcion' es obligatorio"));
    }

    @Test
    @DisplayName("Unhappy: 'descripcion' presente pero vacío -> 400 con 'no debe estar vacío'")
    void crearProducto_400_descripcionVacia() throws Exception {
        String jsonRequest = """
            {
              "nombre": "carro",
              "descripcion": "",
              "precio": 10
            }
            """;

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensaje.status").value(400))
            .andExpect(jsonPath("$.mensaje.error").value("Bad Request"))
            .andExpect(jsonPath("$.fields.descripcion").value("El campo 'descripcion' no debe estar vacío"));
    }

    @Test
    @DisplayName("Unhappy: falta 'precio' -> 400 con 'es obligatorio'")
    void crearProducto_400_faltaPrecio() throws Exception {
        String jsonRequest = """
            {
              "nombre": "carro",
              "descripcion": "rojo"
            }
            """;

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensaje.status").value(400))
            .andExpect(jsonPath("$.mensaje.error").value("Bad Request"))
            .andExpect(jsonPath("$.fields.precio").value("El campo 'precio' es obligatorio"));
    }
}
