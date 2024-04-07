package com.app.clinica.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

import com.app.clinica.dto.ExameRecordDTO;
import com.app.clinica.models.EnfermeiroModel;
import com.app.clinica.models.ExameModel;
import com.app.clinica.repositories.EnfermeiroRepository;
import com.app.clinica.repositories.ExameReposistory;
import com.app.clinica.repositories.PacienteRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ExameController {
    @Autowired
    ExameReposistory exameReposistory;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    EnfermeiroRepository enfermeiroRepository;

    @TokenValidator
    @PostMapping("/exame")
    public ResponseEntity<ExameModel> saveExame(
            @RequestHeader Map<String, String> headers,
            @RequestBody ExameRecordDTO exameRecordDTO) {
        var exameModel = new ExameModel();
        BeanUtils.copyProperties(exameRecordDTO, exameModel);

        EnfermeiroModel enfermeiroModel = enfermeiroRepository
                .findByIdEnfermeiro(UUID.fromString(exameRecordDTO.getIdEnfermeiro()));
        var pacienteModel = pacienteRepository.findByCpf(exameRecordDTO.getCpfPaciente());

        exameModel.setEnfermeiro(enfermeiroModel);
        exameModel.setPaciente(pacienteModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(exameReposistory.save(exameModel));
    }

    @TokenValidator
    @GetMapping("/exame")
    public ResponseEntity<List<ExameModel>> buscarExames(
            @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(exameReposistory.findAll());
    }

    @TokenValidator
    @GetMapping("/exame/{id}")
    public ResponseEntity<Object> buscarConsultaPorId(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<ExameModel> exame = exameReposistory.findById(id);
        if (exame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exame não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(exame.get());
    }

    @TokenValidator
    @PutMapping("exame/{id}")
    public ResponseEntity<Object> atualizaAtestado(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid ExameRecordDTO exameRecordDTO) {
        Optional<ExameModel> exame = exameReposistory.findById(id);
        if (exame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
        }
        var exameModel = exame.get();

        BeanUtils.copyProperties(exameRecordDTO, exameModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(exameReposistory.save(exameModel));
    }

    @TokenValidator
    @DeleteMapping("/exame/{id}")
    public ResponseEntity<Object> deleteExame(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<ExameModel> exame = exameReposistory.findById(id);
        if (exame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("exame not found.");
        }
        exameReposistory.delete(exame.get());
        return ResponseEntity.status(HttpStatus.OK).body("exame deleted successfully.");
    }
}