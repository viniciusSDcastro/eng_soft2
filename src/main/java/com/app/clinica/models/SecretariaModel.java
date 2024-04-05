package com.app.clinica.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "secretaria")
public class SecretariaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSecretaria;
    private String ala;

    @OneToOne
    @JoinColumn(name = "usuario_cpf")
    private UsuarioModel usuario;

    public UUID getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(UUID idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getAla() {
        return ala;
    }

    public void setAla(String ala) {
        this.ala = ala;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
