package io.bootify.administracion_biblioteca.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Administradors")
@Getter
@Setter
public class Administrador extends Usuario {
}
