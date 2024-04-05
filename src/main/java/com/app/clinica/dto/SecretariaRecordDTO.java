package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SecretariaRecordDTO(@NotBlank @NotNull String nome,
        String sobrenome,
        String email,
        String senha,
        String cpf,
        String dataNascimento,
        Boolean ativo,
        String ala,
        String telefone) {
    public String getCpf() {
        return this.cpf;
    }
}
