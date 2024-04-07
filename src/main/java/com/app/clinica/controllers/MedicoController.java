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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.MedicoRecordDTO;
import com.app.clinica.models.MedicoModel;
import com.app.clinica.models.UsuarioModel;
import com.app.clinica.repositories.MedicoRepository;
import com.app.clinica.repositories.UsuarioRepository;
import com.app.clinica.validators.TokenValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MedicoController {
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @TokenValidator
    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoModel>> buscarMedicos(
            @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.findAll());
    }

    @TokenValidator
    @GetMapping("/medicos/{id}")
    public ResponseEntity<Object> buscarMedicoPorId(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id) {
        Optional<MedicoModel> medico0 = medicoRepository.findById(id);
        if (medico0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(medico0.get());
    }

    @TokenValidator
    @GetMapping("/medico_especialidade")
    public ResponseEntity<List<MedicoModel>> buscarPorEspecialidade(
            @RequestHeader Map<String, String> headers,
            @RequestParam String especialidade) {
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.findByEspecialidade(especialidade));
    }

    @TokenValidator
    @PostMapping("/medicos")
    public ResponseEntity<MedicoModel> saveMedico(
            @RequestHeader Map<String, String> headers,
            @RequestBody @Valid MedicoRecordDTO medicoRecordDto) {
        var medicoModel = new MedicoModel();
        BeanUtils.copyProperties(medicoRecordDto, medicoModel);
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(medicoRecordDto, usuarioModel);
        usuarioModel.geraSenha();
        UsuarioModel user = usuarioRepository.save(usuarioModel);
        medicoModel.setUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoRepository.save(medicoModel));
    }

    @TokenValidator
    @PutMapping("medicos/{id}")
    public ResponseEntity<Object> atualizaMedico(
            @RequestHeader Map<String, String> headers,
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid MedicoRecordDTO medicoRecordDTO) {
        Optional<MedicoModel> medico0 = medicoRepository.findById(id);
        if (medico0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        var medicoModel = medico0.get();

        BeanUtils.copyProperties(medicoRecordDTO, medicoModel, "id");
        UsuarioModel usuario0 = medicoModel.getUsuario();
        BeanUtils.copyProperties(medicoRecordDTO, usuario0, "cpf");
        usuarioRepository.save(usuario0);

        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.save(medicoModel));
    }

}
