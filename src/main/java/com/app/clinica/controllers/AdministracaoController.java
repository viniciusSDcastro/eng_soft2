package com.app.clinica.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.AdministracaoRecordDTO;
import com.app.clinica.models.AdministracaoModel;
import com.app.clinica.models.UsuarioModel;
import com.app.clinica.repositories.AdministracaoRepository;
import com.app.clinica.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdministracaoController {
    @Autowired
    AdministracaoRepository administracaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/administracao")
    public ResponseEntity<AdministracaoModel> saveAdministracao(
            @RequestBody @Valid AdministracaoRecordDTO administracaoRecordDTO) {
        var administracaoModel = new AdministracaoModel();
        BeanUtils.copyProperties(administracaoRecordDTO, administracaoModel);
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(administracaoRecordDTO, usuarioModel);
        UsuarioModel user = usuarioRepository.save(usuarioModel);
        administracaoModel.setUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(administracaoRepository.save(administracaoModel));
    }

    @GetMapping("/administracao")
    public ResponseEntity<List<AdministracaoModel>> buscarAdministradores() {
        return ResponseEntity.status(HttpStatus.OK).body(administracaoRepository.findAll());
    }

    @GetMapping("/administracao/{id}")
    public ResponseEntity<Object> buscarAdministradorPorId(@PathVariable(value = "id") UUID id) {
        Optional<AdministracaoModel> administraador = administracaoRepository.findById(id);
        if (administraador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(administraador.get());
    }

    @PutMapping("administracao/{id}")
    public ResponseEntity<Object> atualizaAdministrador(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid AdministracaoRecordDTO administracaoRecordDTO) {
        Optional<AdministracaoModel> administraador = administracaoRepository.findById(id);
        if (administraador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador não encontrado!");
        }
        var administradorModel = administraador.get();

        BeanUtils.copyProperties(administracaoRecordDTO, administradorModel, "id");
        UsuarioModel usuario0 = administradorModel.getUsuario();
        BeanUtils.copyProperties(administracaoRecordDTO, usuario0, "cpf");
        usuarioRepository.save(usuario0);

        return ResponseEntity.status(HttpStatus.OK).body(administracaoRepository.save(administradorModel));
    }

}
