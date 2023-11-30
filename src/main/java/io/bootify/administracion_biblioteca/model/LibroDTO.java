package io.bootify.administracion_biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibroDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String titulo;

    @NotNull
    @Size(max = 255)
    private String autor;

    @NotNull
    private Boolean disponible;

    private Long cliente;

}
