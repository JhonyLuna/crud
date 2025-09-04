package crud.demo.crud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc //es una clase de Spring para simular llamadas HTTP (GET, POST, PUT, DELETE). no se necesita postman para probasr
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Con este objeto, puedes simular peticiones HTTP hacia tu ProductoController.

  @Test
void crearProducto_deberiaRetornar201ConNombreEnElBody() throws Exception {
    // 1. JSON de entrada simulado
    String jsonRequest = """
        {
            "nombre": "pc",
            "precio": 1200
        }
        """;

    // 2. Simular POST y verificar respuesta
    mockMvc.perform(post("/api/productos") //mockMvc.perform ejecuta la peticion HTTP se le pasa la ruta con el metodo en este caso post
                    .contentType(MediaType.APPLICATION_JSON)// .contentType. simula el header como en postmancontentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isCreated()) // ✅ debe ser 201
            .andExpect(jsonPath("$.nombre").value("pc")); // ✅ body debe tener el nombre
            //.andExpect(status().isBadRequest()) // ✅ debe ser 400
            //.andExpect(jsonPath("$[0]").value("precio: El precio debe ser mayor a 0")); // ✅ valida primer error
}

}
