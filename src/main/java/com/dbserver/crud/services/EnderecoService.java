package com.dbserver.crud.services;

import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;


    public List<Endereco> exibeEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return (enderecos);
    }
}
