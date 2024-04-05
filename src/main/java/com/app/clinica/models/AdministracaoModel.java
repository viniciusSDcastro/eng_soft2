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
@Table(name = "administracao")
public class AdministracaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAdministracao;
    private String cargo;

    @OneToOne
    @JoinColumn(name = "usuario_cpf")
    private UsuarioModel usuario;

    public UUID getIdAdministracao() {
        return idAdministracao;
    }

    public void setIdAdministracao(UUID idAdministracao) {
        this.idAdministracao = idAdministracao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
