package io.bootify.administracion_biblioteca.repos;

import io.bootify.administracion_biblioteca.domain.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    boolean existsByUsuarioIgnoreCase(String usuario);

}
