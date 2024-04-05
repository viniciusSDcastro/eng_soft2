package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProntuarioEletronicoRecordDTO(@NotBlank @NotNull String data, String descricao, String cpfPaciente,
        String idEnfermeiro) {
    public String getCpfPaciente() {
        return this.cpfPaciente;
    }

    public String getIdEnfermeiro() {
        return this.idEnfermeiro;
    }
}
