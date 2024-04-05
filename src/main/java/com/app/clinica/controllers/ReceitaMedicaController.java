package com.app.clinica.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.app.clinica.dto.ReceitaMedicaRecordDTO;
import com.app.clinica.models.ConsultaModel;
import com.app.clinica.models.ReceitaMedicaModel;
import com.app.clinica.repositories.ConsultaRepository;
import com.app.clinica.repositories.ReceitaMedicaRepository;

import jakarta.validation.Valid;

@RestController
public class ReceitaMedicaController {
    @Autowired
    ReceitaMedicaRepository receitaMedicaRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @PostMapping("/receita")
    public ResponseEntity<ReceitaMedicaModel> saveReceita(
            @RequestBody @Valid ReceitaMedicaRecordDTO receitaMedicaRecordDTO) {
        var receitaMedica = new ReceitaMedicaModel();
        BeanUtils.copyProperties(receitaMedicaRecordDTO, receitaMedica);
        ConsultaModel consultaModel = consultaRepository
                .findByIdConsulta(UUID.fromString(receitaMedicaRecordDTO.getIdConsulta()));
        receitaMedica.setConsulta(consultaModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(receitaMedicaRepository.save(receitaMedica));
    }

    @GetMapping("/receita")
    public ResponseEntity<List<ReceitaMedicaModel>> buscarReceitas() {
        return ResponseEntity.status(HttpStatus.OK).body(receitaMedicaRepository.findAll());
    }

    @GetMapping("/receita/{id}")
    public ResponseEntity<Object> buscarReceitaPorId(@PathVariable(value = "id") UUID id) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita medica não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(receita.get());
    }

    @PutMapping("receita/{id}")
    public ResponseEntity<Object> atualizaReceita(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ReceitaMedicaRecordDTO receitaMedicaRecordDTO) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita medica não encontrado!");
        }
        var receitaModel = receita.get();

        BeanUtils.copyProperties(receitaMedicaRecordDTO, receitaModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(receitaMedicaRepository.save(receitaModel));
    }

    @DeleteMapping("/receita/{id}")
    public ResponseEntity<Object> deleteReceita(@PathVariable(value = "id") UUID id) {
        Optional<ReceitaMedicaModel> receita = receitaMedicaRepository.findById(id);
        if (receita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("receita not found.");
        }
        receitaMedicaRepository.delete(receita.get());
        return ResponseEntity.status(HttpStatus.OK).body("Receita deleted successfully.");
    }

}
