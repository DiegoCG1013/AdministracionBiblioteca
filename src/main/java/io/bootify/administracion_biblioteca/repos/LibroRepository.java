package io.bootify.administracion_biblioteca.repos;

import io.bootify.administracion_biblioteca.domain.Cliente;
import io.bootify.administracion_biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findFirstByCliente(Cliente cliente);

    boolean existsByClienteId(Long id);

}
