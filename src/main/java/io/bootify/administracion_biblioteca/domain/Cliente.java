package io.bootify.administracion_biblioteca.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Clientes")
@Getter
@Setter
public class Cliente extends Usuario {

    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Libro libroPrestado;

}
