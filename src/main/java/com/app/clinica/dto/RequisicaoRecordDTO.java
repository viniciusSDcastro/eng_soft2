package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequisicaoRecordDTO(@NotBlank @NotNull String titulo, Boolean urgente, String descricao,
        String usuarioCpf) {
    public String getUsuarioCpf() {
        return this.usuarioCpf;
    }

}
