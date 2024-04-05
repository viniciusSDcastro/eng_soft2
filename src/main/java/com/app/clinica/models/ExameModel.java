package com.app.clinica.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exame")
public class ExameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idExame;

    private String tipo;
    private String data;
    private String hora;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cpfPaciente")
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "idEnfermeiro")
    private EnfermeiroModel enfermeiro;

    public UUID getIdExame() {
        return idExame;
    }

    public void setIdExame(UUID idExame) {
        this.idExame = idExame;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
