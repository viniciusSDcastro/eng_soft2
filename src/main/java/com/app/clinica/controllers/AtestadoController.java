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

import com.app.clinica.dto.AtestadoRecordDTO;
import com.app.clinica.models.AtestadoModel;
import com.app.clinica.models.ConsultaModel;
import com.app.clinica.repositories.AtestadoRepository;
import com.app.clinica.repositories.ConsultaRepository;

import jakarta.validation.Valid;

@RestController
public class AtestadoController {
    @Autowired
    AtestadoRepository atestadoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @PostMapping("/atestado")
    public ResponseEntity<AtestadoModel> saveAtestado(@RequestBody @Valid AtestadoRecordDTO atestadoRecordDTO) {
        var atestadoModel = new AtestadoModel();
        BeanUtils.copyProperties(atestadoRecordDTO, atestadoModel);
        ConsultaModel consultaModel = consultaRepository
                .findByIdConsulta(UUID.fromString(atestadoRecordDTO.getIdConsulta()));
        atestadoModel.setConsulta(consultaModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(atestadoRepository.save(atestadoModel));
    }

    @GetMapping("/atestado")
    public ResponseEntity<List<AtestadoModel>> buscarAtestadps() {
        return ResponseEntity.status(HttpStatus.OK).body(atestadoRepository.findAll());
    }

    @GetMapping("/atestado/{id}")
    public ResponseEntity<Object> buscarAtestadoPorId(@PathVariable(value = "id") UUID id) {
        Optional<AtestadoModel> atestado0 = atestadoRepository.findById(id);
        if (atestado0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(atestado0.get());
    }

    @PutMapping("atestado/{id}")
    public ResponseEntity<Object> atualizaAtestado(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid AtestadoRecordDTO atestadoRecordDTO) {
        Optional<AtestadoModel> atestado = atestadoRepository.findById(id);
        if (atestado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
        }
        var atestadoModel = atestado.get();

        BeanUtils.copyProperties(atestadoRecordDTO, atestadoModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(atestadoRepository.save(atestadoModel));
    }

    @DeleteMapping("/atestado/{id}")
    public ResponseEntity<Object> deleteAtestado(@PathVariable(value = "id") UUID id) {
        Optional<AtestadoModel> atestado = atestadoRepository.findById(id);
        if (atestado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("atestado not found.");
        }
        atestadoRepository.delete(atestado.get());
        return ResponseEntity.status(HttpStatus.OK).body("atestado deleted successfully.");
    }

}
