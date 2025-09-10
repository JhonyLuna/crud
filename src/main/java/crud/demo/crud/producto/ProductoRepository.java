package crud.demo.crud.producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository  extends JpaRepository<Producto, Long> {

     boolean existsByNombreIgnoreCase(String nombre); // para validación de unicidad en el post
     boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id); // para validación de unicidad en el put
}  
