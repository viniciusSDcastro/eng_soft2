package com.app.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.PacienteModel;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, String> {
    PacienteModel findByCpf(String cpf);
}
