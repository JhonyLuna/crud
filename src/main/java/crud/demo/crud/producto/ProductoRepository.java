package crud.demo.crud.producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository  extends JpaRepository<Producto, Long> {

   List<Producto> findByNombreContainingIgnoreCase(String q);

}  
