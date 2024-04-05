package com.app.clinica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.EnfermeiroModel;

@Repository
public interface EnfermeiroRepository extends JpaRepository<EnfermeiroModel, UUID> {
    EnfermeiroModel findByIdEnfermeiro(UUID idEnfermeiro);
}
