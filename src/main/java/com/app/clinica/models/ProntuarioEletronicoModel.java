package com.app.clinica.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prontuario_eletronico")
public class ProntuarioEletronicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProntuarioEletronico;
    private String data;
    private String descricao;

    @OneToOne
    @JoinColumn(name = "cpfPaciente")
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "idEnfermeiro")
    private EnfermeiroModel enfermeiro;

    public UUID getIdProntuarioEletronico() {
        return idProntuarioEletronico;
    }

    public void setIdProntuarioEletronico(UUID idProntuarioEletronico) {
        this.idProntuarioEletronico = idProntuarioEletronico;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }

    public EnfermeiroModel getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(EnfermeiroModel enfermeiro) {
        this.enfermeiro = enfermeiro;
    }
}
