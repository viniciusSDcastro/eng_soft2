package com.app.clinica.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "requisicao")
public class RequisicaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idRequisicao;
    private String titulo;
    private Boolean urgente;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_cpf")
    private UsuarioModel usuario;

    public UUID getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(UUID idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Boolean getUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
