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
@Table(name = "atestado")
public class AtestadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAtestado;
    private String dataInicio;
    private String dataFim;
    private String descricao;

    @OneToOne
    @JoinColumn(name = "idConsulta")
    private ConsultaModel consulta;

    public UUID getIdAtestado() {
        return idAtestado;
    }

    public void setIdAtestado(UUID idAtestado) {
        this.idAtestado = idAtestado;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ConsultaModel getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaModel consulta) {
        this.consulta = consulta;
    }
}
