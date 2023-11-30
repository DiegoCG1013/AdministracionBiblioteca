package io.bootify.administracion_biblioteca.repos;

import io.bootify.administracion_biblioteca.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByUsuarioIgnoreCase(String usuario);

}
