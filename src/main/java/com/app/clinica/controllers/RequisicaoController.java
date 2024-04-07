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

import com.app.clinica.dto.RequisicaoRecordDTO;
import com.app.clinica.models.RequisicaoModel;
import com.app.clinica.repositories.RequisicaoRepository;
import com.app.clinica.repositories.UsuarioRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RequisicaoController {
    @Autowired
    RequisicaoRepository requisicaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @TokenValidator
    @PostMapping("/requisicao")
    public ResponseEntity<RequisicaoModel> saveRequisicao(@RequestBody @Valid RequisicaoRecordDTO requisicaoRecordDTO,
            @RequestHeader Map<String, String> headers) {
        var requisicaoModel = new RequisicaoModel();
        BeanUtils.copyProperties(requisicaoRecordDTO, requisicaoModel);
        var usuarioModel = usuarioRepository.findByCpf(requisicaoRecordDTO.getUsuarioCpf());
        requisicaoModel.setUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(requisicaoRepository.save(requisicaoModel));
    }

    @TokenValidator
    @GetMapping("/requisicao")
    public ResponseEntity<List<RequisicaoModel>> buscarRequisicao(@RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(requisicaoRepository.findAll());
    }

    @TokenValidator
    @GetMapping("/requisicao/{id}")
    public ResponseEntity<Object> buscarRequisicaoPorId(@PathVariable(value = "id") UUID id,
            @RequestHeader Map<String, String> headers) {
        Optional<RequisicaoModel> requisicao = requisicaoRepository.findById(id);
        if (requisicao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(requisicao.get());
    }

    @TokenValidator
    @PutMapping("requisicao/{id}")
    public ResponseEntity<Object> atualizaRequisicao(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid RequisicaoRecordDTO requisicaoRecordDTO, @RequestHeader Map<String, String> headers) {
        Optional<RequisicaoModel> consulta = requisicaoRepository.findById(id);
        if (consulta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
        }
        var requisicaoModel = consulta.get();

        BeanUtils.copyProperties(requisicaoRecordDTO, requisicaoModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(requisicaoRepository.save(requisicaoModel));
    }

    @TokenValidator
    @DeleteMapping("/requisicao/{id}")
    public ResponseEntity<Object> deleteRequisicao(@PathVariable(value = "id") UUID id,
            @RequestHeader Map<String, String> headers) {
        Optional<RequisicaoModel> requisicao = requisicaoRepository.findById(id);
        if (requisicao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisicao not found.");
        }
        requisicaoRepository.delete(requisicao.get());
        return ResponseEntity.status(HttpStatus.OK).body("Requisicao deleted successfully.");
    }

}
