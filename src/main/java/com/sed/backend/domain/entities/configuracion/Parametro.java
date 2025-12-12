package com.sed.backend.domain.entities.configuracion;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parametros")
public class Parametro extends AuditableEntity {

    @Column(name = "clave", nullable = false, unique = true)
    private String clave;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "descripcion")
    private String descripcion;
}