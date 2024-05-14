package com.dbserver.crud.services;

import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.CpfExistenteException;
import com.dbserver.crud.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;


    public List<Endereco> exibeEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return (enderecos);
    }

    public Optional<Endereco> exibeEnderecoPorId(Long id) {
        return enderecoRepository.findById(id);
    }

}
