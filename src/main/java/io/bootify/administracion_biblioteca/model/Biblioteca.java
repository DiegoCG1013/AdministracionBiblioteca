package io.bootify.administracion_biblioteca.model;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Biblioteca {

    @Valid
    private List<LibroDTO> libros;

    @Valid
    private List<BibliotecarioDTO> bibliotecarios;

    @Valid
    private List<AdministradorDTO> administradores;

    @Valid
    private List<ClienteDTO> clientes;

}
