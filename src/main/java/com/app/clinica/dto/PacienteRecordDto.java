package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacienteRecordDto(@NotBlank @NotNull String nome,
        String cpf,
        String dataNascimento,
        String telefone,
        String email) {

}
