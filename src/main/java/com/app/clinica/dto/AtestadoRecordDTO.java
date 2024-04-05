package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtestadoRecordDTO(@NotBlank @NotNull String dataInicio, String dataFim, String descricao,
        String idConsulta) {
    public String getIdConsulta() {
        return this.idConsulta;
    }
}
