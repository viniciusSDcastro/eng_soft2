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
@Table(name = "medico")
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idMedico;
    private String crm;
    private String especialidade;

    @OneToOne
    @JoinColumn(name = "usuario_cpf")
    private UsuarioModel usuario;

    public UUID getIdMedico() {
        return this.idMedico;
    }

    public void setIdMedico(UUID id) {
        this.idMedico = id;
    }

    public String getCrm() {
        return this.crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public UsuarioModel getUsuario() {
        return this.usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

}
