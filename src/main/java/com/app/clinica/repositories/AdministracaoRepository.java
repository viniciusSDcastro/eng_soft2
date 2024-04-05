package com.app.clinica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.AdministracaoModel;

@Repository
public interface AdministracaoRepository extends JpaRepository<AdministracaoModel, UUID> {

}
