package com.dbserver.crud.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;
    private LocalDate nascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    public Pessoa(){
    }

    public Pessoa(String nome, String cpf, LocalDate nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.enderecos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }
    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<Endereco> getEndereco() {
        return enderecos;
    }
    public void setEndereco(List<Endereco> endereco) {
        this.enderecos = endereco;
    }
}
