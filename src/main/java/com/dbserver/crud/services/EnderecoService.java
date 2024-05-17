package com.dbserver.crud.services;

import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.exceptions.EnderecoNaoEncontradoException;
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

    public Endereco modificaEndereco(Endereco endereco) throws EnderecoNaoEncontradoException {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(endereco.getId());
        if (enderecoOptional.isPresent()) {
            Endereco enderecoSalvo = enderecoOptional.get();
            enderecoSalvo.setRua(endereco.getRua());
            enderecoSalvo.setNumero(endereco.getNumero());
            enderecoSalvo.setComplemento(endereco.getComplemento());
            enderecoSalvo.setBairro(endereco.getBairro());
            enderecoSalvo.setCidade(endereco.getCidade());
            enderecoSalvo.setEstado(endereco.getEstado());
            enderecoSalvo.setCep(endereco.getCep());
            return enderecoRepository.save(enderecoSalvo);
        } else {
            throw new EnderecoNaoEncontradoException("Endereço não encontrado pelo ID informado.");
        }
    }

    public void exclueEndereco(Long id) throws EnderecoNaoEncontradoException {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado com o ID: " + id.toString()));
        enderecoRepository.delete(endereco);
    }
}
