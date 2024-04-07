package com.app.clinica.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
// import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;

import org.mindrot.jbcrypt.BCrypt;

import com.app.clinica.utils.Jwt;

// @MappedSuperclass
@Entity // Adicionando @Entity para mapear a classe para o banco de dados
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class UsuarioModel implements Serializable {

    @Id
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String telefone;
    private String dataNascimento;
    private Boolean ativo;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void atualizaSenha(String senha) {
        var senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());
        this.senha = senhaCriptografada;
    }

    public void geraSenha() {
        var senhaPadrao = this.cpf.substring(0, 3) + '@' + this.nome.substring(0, 2).toLowerCase();
        System.out.println(senhaPadrao);
        var senhaCriptografada = BCrypt.hashpw(senhaPadrao, BCrypt.gensalt());
        this.senha = senhaCriptografada;
    }

    public boolean comparaSenha(String senha) {
        return BCrypt.checkpw(senha, this.senha);
    }

    public String gerarToken() {
        return Jwt.generateJwt(this.cpf);
    }
}
