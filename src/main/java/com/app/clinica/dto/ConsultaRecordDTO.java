package com.app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaRecordDTO(@NotBlank @NotNull String data, String hora, String cpfPaciente, String idMedico) {
    public String getCpfPaciente() {
        return this.cpfPaciente;
    }

    public String getIdMedico() {
        return this.idMedico;
    }
}
