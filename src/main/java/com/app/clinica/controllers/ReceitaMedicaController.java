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

import com.app.clinica.dto.ReceitaMedicaRecordDTO;
import com.app.clinica.models.ConsultaModel;
import com.app.clinica.models.ReceitaMedicaModel;
import com.app.clinica.repositories.ConsultaRepository;
import com.app.clinica.repositories.ReceitaMedicaRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReceitaMedicaController {
    @Autowired
    ReceitaMedicaRepository receitaMedicaRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @TokenValidator
    @PostMapping("/receita")
    public ResponseEntity<ReceitaMedicaModel> saveReceita(
            @RequestHeader Map<String, String> headers,
            @RequestBody @Valid ReceitaMedicaRecordDTO receitaMedicaRecordDTO) {
        var receitaMedica = new ReceitaMedicaModel();
        BeanUtils.copyProperties(receitaMedicaRecordDTO, receitaMedica);
        ConsultaModel consultaModel = consultaRepository
                .findByIdConsulta(UUID.fromString(receitaMedicaRecordDTO.getIdConsulta()));
        receitaMedica.setConsulta(consultaModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(receitaMedicaRepository.save(receitaMedica));
    }

    @TokenValidator
    @GetMapping("/receita")
    public ResponseEntity<List<ReceitaMedicaModel>> buscarReceitas(
            @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(receitaMedicaRepository.findAll());
    }

    @TokenValidator
    @GetMapping("/receita/{id}")
    public ResponseEntity<Object> buscarReceitaPorId(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita medica não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(receita.get());
    }

    @TokenValidator
    @PutMapping("receita/{id}")
    public ResponseEntity<Object> atualizaReceita(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid ReceitaMedicaRecordDTO receitaMedicaRecordDTO) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita medica não encontrado!");
        }
        var receitaModel = receita.get();

        BeanUtils.copyProperties(receitaMedicaRecordDTO, receitaModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(receitaMedicaRepository.save(receitaModel));
    }

    @TokenValidator
    @DeleteMapping("/receita/{id}")
    public ResponseEntity<Object> deleteReceita(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("receita not found.");
        }
        receitaMedicaRepository.delete(receita.get());
        return ResponseEntity.status(HttpStatus.OK).body("Receita deleted successfully.");
    }
}
