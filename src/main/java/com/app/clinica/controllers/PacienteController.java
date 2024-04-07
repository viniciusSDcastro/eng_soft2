package com.app.clinica.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.PacienteRecordDto;
import com.app.clinica.models.PacienteModel;
import com.app.clinica.repositories.PacienteRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PacienteController {
    @Autowired
    PacienteRepository pacienteRepository;

    @TokenValidator
    @PostMapping("/paciente")
    public ResponseEntity<PacienteModel> savePaciente(@RequestBody @Valid PacienteRecordDto pacienteRecordDto,
            @RequestHeader Map<String, String> headers) {
        var pacienteModel = new PacienteModel();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(pacienteModel));
    }

    @TokenValidator
    @GetMapping("/paciente")
    public ResponseEntity<List<PacienteModel>> buscarPacientes() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.findAll());
    }

    @TokenValidator
    @GetMapping("paciente/{id}")
    public ResponseEntity<Object> buscarMedicoPorId(@PathVariable(value = "id") String id) {
        Optional<PacienteModel> paciente0 = pacienteRepository.findById(id);
        if (paciente0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(paciente0.get());
    }

    @TokenValidator
    @PutMapping("paciente/{id}")
    public ResponseEntity<Object> atualizaPaciente(@PathVariable(value = "id") String id,
            @RequestBody @Valid PacienteRecordDto pacienteRecordDto,
            @RequestHeader Map<String, String> headers) {
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        var pacienteModel = paciente.get();

        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(pacienteModel));
    }

    @TokenValidator
    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(value = "id") String id,
            @RequestHeader Map<String, String> headers) {
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente not found.");
        }
        pacienteRepository.delete(paciente.get());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente deleted successfully.");
    }
}
