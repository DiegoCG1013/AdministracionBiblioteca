package io.bootify.administracion_biblioteca.repos;

import io.bootify.administracion_biblioteca.domain.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    boolean existsByUsuarioIgnoreCase(String usuario);

}
