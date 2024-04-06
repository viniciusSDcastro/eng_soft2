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
        if (usuario.getSenha() != loginRecordDTO.getPassword()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login não é possivel");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
    }

}
