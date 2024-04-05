package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReceitaMedicaRecordDTO(@NotBlank @NotNull String titulo, String descricao, String validade,
        String idConsulta) {
    public String getIdConsulta() {
        return this.idConsulta;
    }

}
