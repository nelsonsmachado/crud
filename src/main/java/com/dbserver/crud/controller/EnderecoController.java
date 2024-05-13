package com.dbserver.crud.controller;


import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.repositories.EnderecoRepository;
import com.dbserver.crud.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
