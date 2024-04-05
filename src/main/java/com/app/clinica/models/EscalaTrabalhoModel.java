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
@Table(name = "escala_trabalho")
public class EscalaTrabalhoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEscalaTrabalho;
    private String horaInicio;
    private String horaFim;
    private String dia;

    @ManyToOne
    @JoinColumn(name = "usuario_cpf")
    private UsuarioModel usuario;

    public UUID getIdEscalaTrabalho() {
        return idEscalaTrabalho;
    }

    public void setIdEscalaTrabalho(UUID idEscalaTrabalho) {
        this.idEscalaTrabalho = idEscalaTrabalho;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
