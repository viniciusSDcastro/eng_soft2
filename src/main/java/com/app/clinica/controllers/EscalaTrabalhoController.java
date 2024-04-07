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

import com.app.clinica.dto.EscalaTrabalhoDTO;
import com.app.clinica.models.EscalaTrabalhoModel;
import com.app.clinica.repositories.EscalaTrabalhoRepository;
import com.app.clinica.repositories.UsuarioRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EscalaTrabalhoController {

    @Autowired
    private EscalaTrabalhoRepository escalaTrabalhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @TokenValidator
    @PostMapping("/escala")
    public ResponseEntity<EscalaTrabalhoModel> saveEscalaTrabalho(
            @RequestHeader Map<String, String> headers,
            @RequestBody @Valid EscalaTrabalhoDTO escalaTrabalhoDTO) {
        var escalaTrabalhoModel = new EscalaTrabalhoModel();
        BeanUtils.copyProperties(escalaTrabalhoDTO, escalaTrabalhoModel);
        var usuarioModel = usuarioRepository.findByCpf(escalaTrabalhoDTO.getCpf());
        escalaTrabalhoModel.setUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(escalaTrabalhoRepository.save(escalaTrabalhoModel));
    }

    @TokenValidator
    @GetMapping("/escala")
    public ResponseEntity<List<EscalaTrabalhoModel>> buscarEscalasTrabalho(
            @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(escalaTrabalhoRepository.findAll());
    }

    @TokenValidator
    @GetMapping("/escala/{id}")
    public ResponseEntity<Object> buscarEscalaTrabalhoPorId(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<EscalaTrabalhoModel> escala = escalaTrabalhoRepository.findById(id);
        if (escala.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(escala.get());
    }

    @TokenValidator
    @PutMapping("escala/{id}")
    public ResponseEntity<Object> atualizaEscala(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid EscalaTrabalhoDTO escalaTrabalhoDTO) {
        Optional<EscalaTrabalhoModel> escala = escalaTrabalhoRepository.findById(id);
        if (escala.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Escala de trabalho não encontrado!");
        }
        var escalaModel = escala.get();

        BeanUtils.copyProperties(escalaTrabalhoDTO, escalaModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(escalaTrabalhoRepository.save(escalaModel));
    }

    @TokenValidator
    @DeleteMapping("/escala/{id}")
    public ResponseEntity<Object> deleteEscala(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<EscalaTrabalhoModel> escala = escalaTrabalhoRepository.findById(id);
        if (escala.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("escala de trabalho not found.");
        }
        escalaTrabalhoRepository.delete(escala.get());
        return ResponseEntity.status(HttpStatus.OK).body("escala de trabalho deleted successfully.");
    }

}
