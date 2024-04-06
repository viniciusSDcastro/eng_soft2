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

import com.app.clinica.dto.ProntuarioEletronicoRecordDTO;
import com.app.clinica.models.EnfermeiroModel;
import com.app.clinica.models.PacienteModel;
import com.app.clinica.models.ProntuarioEletronicoModel;
import com.app.clinica.repositories.EnfermeiroRepository;
import com.app.clinica.repositories.PacienteRepository;
import com.app.clinica.repositories.ProntuarioEletronicoRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProntuarioEletronicoController {
        @Autowired
        ProntuarioEletronicoRepository prontuarioEletronicoRepository;

        @Autowired
        PacienteRepository pacienteRepository;

        @Autowired
        EnfermeiroRepository enfermeiroRepository;

        @PostMapping("/prontuario")
        public ResponseEntity<ProntuarioEletronicoModel> saveProntuario(
                        @RequestBody @Valid ProntuarioEletronicoRecordDTO prontuarioEletronicoRecordDTO) {
                var prontuarioEletronicoModel = new ProntuarioEletronicoModel();
                BeanUtils.copyProperties(prontuarioEletronicoRecordDTO, prontuarioEletronicoModel);
                PacienteModel paciente = pacienteRepository.findByCpf(prontuarioEletronicoRecordDTO.getCpfPaciente());
                EnfermeiroModel enfermeiro = enfermeiroRepository
                                .findByIdEnfermeiro(UUID.fromString((prontuarioEletronicoRecordDTO.getIdEnfermeiro())));

                prontuarioEletronicoModel.setPaciente(paciente);
                prontuarioEletronicoModel.setEnfermeiro(enfermeiro);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(prontuarioEletronicoRepository.save(prontuarioEletronicoModel));
        }

        @GetMapping("/prontuario")
        public ResponseEntity<List<ProntuarioEletronicoModel>> buscarProntuarios() {
                return ResponseEntity.status(HttpStatus.OK).body(prontuarioEletronicoRepository.findAll());
        }

        @GetMapping("/prontuario/{id}")
        public ResponseEntity<Object> buscarConsultaPorId(@PathVariable(value = "id") UUID id) {
                Optional<ProntuarioEletronicoModel> prontuario = prontuarioEletronicoRepository.findById(id);
                if (prontuario.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body("Prontuario Eletronico não encontrado!");
                }
                return ResponseEntity.status(HttpStatus.OK).body(prontuario.get());
        }

        @PutMapping("prontuario/{id}")
        public ResponseEntity<Object> atualizaProntuario(@PathVariable(value = "id") UUID id,
                        @RequestBody @Valid ProntuarioEletronicoRecordDTO prontuarioEletronicoRecordDTO) {
                Optional<ProntuarioEletronicoModel> prontuario = prontuarioEletronicoRepository.findById(id);
                if (prontuario.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atestado não encontrado!");
                }
                var prontuarioModel = prontuario.get();

                BeanUtils.copyProperties(prontuarioEletronicoRecordDTO, prontuarioModel, "id");
                return ResponseEntity.status(HttpStatus.OK).body(prontuarioEletronicoRepository.save(prontuarioModel));
        }

        @DeleteMapping("/prontuario/{id}")
        public ResponseEntity<Object> deleteProntuario(@PathVariable(value = "id") UUID id) {
                Optional<ProntuarioEletronicoModel> prontuario = prontuarioEletronicoRepository.findById(id);
                if (prontuario.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prontuario eletronico not found.");
                }
                prontuarioEletronicoRepository.delete(prontuario.get());
                return ResponseEntity.status(HttpStatus.OK).body("Prontuario eletronico deleted successfully.");
        }

}
