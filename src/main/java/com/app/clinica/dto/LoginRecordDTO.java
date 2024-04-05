package com.app.clinica.dto;

public record LoginRecordDTO(String email, String senha) {
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.senha;
    }
}
