package crud.demo.crud.producto;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
        // 1) Recolecta errores por campo con mensajes diferenciados
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            String field = fe.getField();
            Object rejected = fe.getRejectedValue(); // null si el campo NO vino en el JSON
            String code = fe.getCode(); // "NotNull", "NotBlank", "Size", "Positive", etc.

            String msg;
            if ("NotNull".equals(code) || "NotBlank".equals(code) || "NotEmpty".equals(code)) {
                if (rejected == null) {
                    // No vino en el JSON
                    msg = "El campo '" + field + "' es obligatorio";
                } else if (rejected instanceof String s && s.trim().isEmpty()) {
                    // Vino, pero vacío o solo espacios
                    msg = "El campo '" + field + "' no debe estar vacío";
                } else {
                    // Otro caso (por si acaso), usa el mensaje de la anotación
                    msg = fe.getDefaultMessage();
                }
            } else {
                // Para otras reglas (Size, Pattern, Digits, DecimalMin, Positive, etc.)
                msg = fe.getDefaultMessage();
            }

            fields.putIfAbsent(field, msg); // conserva el primer mensaje por campo
        });

        // 2) Arma el JSON final { mensaje{...}, fields{...} }
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> mensaje = new LinkedHashMap<>();
        mensaje.put("status", 400);
        mensaje.put("error", "Bad Request");

        root.put("mensaje", mensaje);
        root.put("fields", fields);

        return ResponseEntity.badRequest().body(root);
    }

}
