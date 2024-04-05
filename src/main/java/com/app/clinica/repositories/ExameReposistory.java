package com.app.clinica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.ExameModel;

@Repository
public interface ExameReposistory extends JpaRepository<ExameModel, UUID> {
    ExameModel findByIdExame(UUID idExame);
}
