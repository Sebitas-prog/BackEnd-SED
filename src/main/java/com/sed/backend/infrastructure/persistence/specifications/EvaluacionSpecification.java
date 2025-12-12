package com.sed.backend.infrastructure.persistence.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;
import com.sed.backend.domain.enums.CanalEnum;
import com.sed.backend.domain.enums.EstadoEvaluacionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public final class EvaluacionSpecification {

    private EvaluacionSpecification() {
    }

    public static Specification<Evaluacion> porEstado(EstadoEvaluacionEnum estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<Evaluacion> porCanal(CanalEnum canal) {
        return (root, query, cb) -> canal == null ? null : cb.equal(root.get("canal"), canal);
    }

    public static Specification<Evaluacion> porSeccion(UUID seccionId) {
        return (root, query, cb) -> seccionId == null ? null : cb.equal(root.get("seccion").get("id"), seccionId);
    }

    public static Specification<Evaluacion> completadasDesde(LocalDateTime fechaInicio) {
        return (root, query, cb) -> fechaInicio == null ? null
                : cb.greaterThanOrEqualTo(root.get("completadoEn"), fechaInicio);
    }
}