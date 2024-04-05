package com.app.clinica.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.PacienteRecordDto;
import com.app.clinica.models.PacienteModel;
import com.app.clinica.repositories.PacienteRepository;

import jakarta.validation.Valid;

@RestController
public class PacienteController {
    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping("/paciente")
    public ResponseEntity<PacienteModel> savePaciente(@RequestBody @Valid PacienteRecordDto pacienteRecordDto) {
        var pacienteModel = new PacienteModel();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        // var usuarioModel = new UsuarioModel();
        // BeanUtils.copyProperties(enfermeiroRecordDTO, usuarioModel);
        // UsuarioModel user = usuarioRepository.save(usuarioModel);
        // enfermeiroModel.setUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(pacienteModel));
    }

    @GetMapping("/paciente")
    public ResponseEntity<List<PacienteModel>> buscarPacientes() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.findAll());
    }

    @GetMapping("paciente/{id}")
    public ResponseEntity<Object> buscarMedicoPorId(@PathVariable(value = "id") String id) {
        Optional<PacienteModel> paciente0 = pacienteRepository.findById(id);
        if (paciente0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(paciente0.get());
    }

    @PutMapping("paciente/{id}")
    public ResponseEntity<Object> atualizaAtestado(@PathVariable(value = "id") String id,
            @RequestBody @Valid PacienteRecordDto pacienteRecordDto) {
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
        }
        var pacienteModel = paciente.get();

        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(pacienteModel));
    }

    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(value = "id") String id) {
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("paciente not found.");
        }
        pacienteRepository.delete(paciente.get());
        return ResponseEntity.status(HttpStatus.OK).body("paciente deleted successfully.");
    }

}
