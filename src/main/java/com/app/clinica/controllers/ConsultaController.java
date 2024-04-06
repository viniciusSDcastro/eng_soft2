package com.app.clinica.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.ConsultaRecordDTO;
import com.app.clinica.models.ConsultaModel;
import com.app.clinica.models.MedicoModel;
import com.app.clinica.repositories.ConsultaRepository;
import com.app.clinica.repositories.MedicoRepository;
import com.app.clinica.repositories.PacienteRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ConsultaController {
    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping("/consulta")
    public ResponseEntity<ConsultaModel> saveConsulta(@RequestBody @Valid ConsultaRecordDTO consultaRecordDTO) {
        // TODO: process POST request
        var consultaModel = new ConsultaModel();
        BeanUtils.copyProperties(consultaRecordDTO, consultaModel);
        MedicoModel medicoModel = medicoRepository.findByIdMedico(UUID.fromString(consultaRecordDTO.getIdMedico()));
        var pacienteModel = pacienteRepository.findByCpf(consultaRecordDTO.cpfPaciente());

        consultaModel.setMedico(medicoModel);
        consultaModel.setPaciente(pacienteModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consultaModel));
    }

    @GetMapping("/consulta")
    public ResponseEntity<List<ConsultaModel>> buscarConsultas() {
        return ResponseEntity.status(HttpStatus.OK).body(consultaRepository.findAll());
    }

    @GetMapping("/consulta/{id}")
    public ResponseEntity<Object> buscarConsultaPorId(@PathVariable(value = "id") UUID id) {
        Optional<ConsultaModel> consulta0 = consultaRepository.findById(id);
        if (consulta0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medico não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(consulta0.get());
    }

    @PutMapping("consulta/{id}")
    public ResponseEntity<Object> atualizaAtestado(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ConsultaRecordDTO consultaRecordDTO) {
        Optional<ConsultaModel> consulta = consultaRepository.findById(id);
        if (consulta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
        }
        var consultaModel = consulta.get();

        BeanUtils.copyProperties(consultaRecordDTO, consultaModel, "id");
        return ResponseEntity.status(HttpStatus.OK).body(consultaRepository.save(consultaModel));
    }

    @DeleteMapping("/consulta/{id}")
    public ResponseEntity<Object> deleteConsulta(@PathVariable(value = "id") UUID id) {
        Optional<ConsultaModel> consulta = consultaRepository.findById(id);
        if (consulta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("consulta not found.");
        }
        consultaRepository.delete(consulta.get());
        return ResponseEntity.status(HttpStatus.OK).body("consulta deleted successfully.");
    }
}
