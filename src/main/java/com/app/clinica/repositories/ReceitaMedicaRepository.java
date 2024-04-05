package com.app.clinica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.ReceitaMedicaModel;

@Repository
public interface ReceitaMedicaRepository extends JpaRepository<ReceitaMedicaModel, UUID> {

}
