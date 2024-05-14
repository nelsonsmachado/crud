package com.dbserver.crud.controller;


import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.EnderecoNaoEncontradoException;
import com.dbserver.crud.exceptions.UsuarioNaoEncontradoException;
import com.dbserver.crud.repositories.EnderecoRepository;
import com.dbserver.crud.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {


    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/todos")
    public ResponseEntity<List<Endereco>> exibeEnderecos() {
        List<Endereco> enderecos = enderecoService.exibeEnderecos();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> exibeEnderecoPorId(@PathVariable Long id) {
        Optional<Endereco> enderecoPorId = enderecoService.exibeEnderecoPorId(id);
        return enderecoPorId.map(endereco -> new ResponseEntity<>(endereco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclueEndereco(@PathVariable Long id) {
        try {
            enderecoService.exclueEndereco(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EnderecoNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
