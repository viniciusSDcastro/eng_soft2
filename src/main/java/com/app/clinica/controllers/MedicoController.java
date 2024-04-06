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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.MedicoRecordDTO;
import com.app.clinica.models.MedicoModel;
import com.app.clinica.models.UsuarioModel;
import com.app.clinica.repositories.MedicoRepository;
import com.app.clinica.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MedicoController {
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoModel>> buscarMedicos() {
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.findAll());
    }

    @GetMapping("/medicos/{id}")
    public ResponseEntity<Object> buscarMedicoPorId(@PathVariable(value = "id") UUID id) {
        Optional<MedicoModel> medico0 = medicoRepository.findById(id);
        if (medico0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(medico0.get());
    }

    @GetMapping("/medico_especialidade")
    public ResponseEntity<List<MedicoModel>> buscarPorEspecialidade(@RequestParam String especialidade) {
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.findByEspecialidade(especialidade));
    }

    @PostMapping("/medicos")
    public ResponseEntity<MedicoModel> saveMedico(@RequestBody @Valid MedicoRecordDTO medicoRecordDto) {
        var medicoModel = new MedicoModel();
        BeanUtils.copyProperties(medicoRecordDto, medicoModel);
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(medicoRecordDto, usuarioModel);
        UsuarioModel user = usuarioRepository.save(usuarioModel);
        medicoModel.setUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoRepository.save(medicoModel));
    }

    @PutMapping("medicos/{id}")
    public ResponseEntity<Object> atualizaMedico(@PathVariable(value = "id") UUID id,
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
