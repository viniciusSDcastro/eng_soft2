package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EscalaTrabalhoDTO(@NotBlank @NotNull String horaInicio, String horaFim, String cpf, String dia) {
    public String getCpf() {
        return this.cpf;
    }
}
