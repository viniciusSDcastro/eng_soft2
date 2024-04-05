package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdministracaoRecordDTO(@NotBlank @NotNull String nome,
        String sobrenome,
        String email,
        String senha,
        String cpf,
        String dataNascimento,
        Boolean ativo,
        String especialidade,
        String crm,
        String telefone) {
    public String getCpf() {
        return this.cpf;
    }
}
