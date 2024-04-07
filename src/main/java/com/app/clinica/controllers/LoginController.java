package com.app.clinica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.clinica.dto.LoginRecordDTO;
import com.app.clinica.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> realizarLogin(@RequestBody LoginRecordDTO loginRecordDTO) {
        var usuario = usuarioRepository.findByEmail(loginRecordDTO.getEmail());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login não é possivel");
        }

        var senhaValida = usuario.comparaSenha(loginRecordDTO.getPassword());

        if (!senhaValida) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login não é possivel");
        }

        @SuppressWarnings("unused")
        Object response = new Object() {
            public String token = usuario.gerarToken();
            public String nome = usuario.getNome();
            public String sobrenome = usuario.getSobrenome();
            public String email = usuario.getEmail();
            public String cpf = usuario.getCpf();
            public String telefone = usuario.getTelefone();
            public String dataNascimento = usuario.getDataNascimento();
        };

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
