package com.dbserver.crud.services;

import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.CpfExistenteException;
import com.dbserver.crud.exceptions.UsuarioNaoEncontradoException;
import com.dbserver.crud.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa adicionaPessoa(Pessoa novaPessoa) {
        if (pessoaRepository.existsByCpf(novaPessoa.getCpf())) {
            throw new CpfExistenteException("Já existe um usuário com este CPF.");
        }
        return pessoaRepository.save(novaPessoa);
    }

    public List<Pessoa> exibePessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return (pessoas);
    }

    public Optional<Pessoa> exibePessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public void excluePessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        pessoaRepository.delete(pessoa);
    }
 }

