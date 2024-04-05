package com.app.clinica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.ConsultaModel;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, UUID> {
    ConsultaModel findByIdConsulta(UUID idConsulta);
}
