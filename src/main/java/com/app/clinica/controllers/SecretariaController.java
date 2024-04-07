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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.SecretariaRecordDTO;
import com.app.clinica.models.SecretariaModel;
import com.app.clinica.models.UsuarioModel;
import com.app.clinica.repositories.SecretariaRepository;
import com.app.clinica.repositories.UsuarioRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SecretariaController {
    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @TokenValidator
    @PostMapping("/secretaria")
    public ResponseEntity<SecretariaModel> savSecretaria(@RequestBody @Valid SecretariaRecordDTO secretariaRecordDTO,
            @RequestHeader Map<String, String> headers) {
        var secretariaModel = new SecretariaModel();
        BeanUtils.copyProperties(secretariaRecordDTO, secretariaModel);
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(secretariaRecordDTO, usuarioModel);
        usuarioModel.geraSenha();
        UsuarioModel user = usuarioRepository.save(usuarioModel);
        secretariaModel.setUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(secretariaRepository.save(secretariaModel));
    }

    @TokenValidator
    @GetMapping("/secretaria")
    public ResponseEntity<List<SecretariaModel>> buscarSecretaria(@RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(secretariaRepository.findAll());
    }

    @TokenValidator
    @GetMapping("/secretaria/{id}")
    public ResponseEntity<Object> buscarSecretariaPorId(@PathVariable(value = "id") UUID id,
            @RequestHeader Map<String, String> headers) {
        Optional<SecretariaModel> secretario0 = secretariaRepository.findById(id);
        if (secretario0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secretaria não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(secretario0.get());
    }

    @TokenValidator
    @PutMapping("secretaria/{id}")
    public ResponseEntity<Object> atualizaSecretaria(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SecretariaRecordDTO secretariaRecordDTO, @RequestHeader Map<String, String> headers) {
        Optional<SecretariaModel> secretario0 = secretariaRepository.findById(id);
        if (secretario0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secretaria não encontrado!");
        }
        var secretariaModel = secretario0.get();

        BeanUtils.copyProperties(secretariaRecordDTO, secretariaModel, "id");
        UsuarioModel usuario0 = secretariaModel.getUsuario();
        BeanUtils.copyProperties(secretariaRecordDTO, usuario0, "cpf");
        usuarioRepository.save(usuario0);

        return ResponseEntity.status(HttpStatus.OK).body(secretariaRepository.save(secretariaModel));
    }
}
