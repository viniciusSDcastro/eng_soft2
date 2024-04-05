package com.app.clinica.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receita_medica")
public class ReceitaMedicaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idReceita;
    private String titulo;
    private String descricao;
    private String validade;

    @OneToOne
    @JoinColumn(name = "idConsulta")
    private ConsultaModel consulta;

    public UUID getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(UUID idReceita) {
        this.idReceita = idReceita;
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

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public ConsultaModel getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaModel consulta) {
        this.consulta = consulta;
    }

}
