package com.app.clinica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.MedicoModel;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, UUID> {
    MedicoModel findByIdMedico(UUID idMedico);

    List<MedicoModel> findByEspecialidade(String especialidade);
}
