package io.bootify.administracion_biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BibliotecarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String usuario;

    @NotNull
    @Size(max = 255)
    private String contrasenya;

}
