package com.app.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.clinica.models.UsuarioModel;

// import com.clinica.app.models.ProductModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    UsuarioModel findByCpf(String cpf);

    UsuarioModel findByEmail(String email);
}