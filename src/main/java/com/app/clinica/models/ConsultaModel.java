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
@Table(name = "consulta")
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idConsulta;
    private String data;
    private String hora;

    @ManyToOne
    @JoinColumn(name = "cpfPaciente")
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "idMedico")
    private MedicoModel medico;

    public UUID getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(UUID idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getData() {
        return this.data;
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

    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setMedico(MedicoModel medico) {
        this.medico = medico;
    }

    public MedicoModel getMedico() {
        return medico;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }

}
