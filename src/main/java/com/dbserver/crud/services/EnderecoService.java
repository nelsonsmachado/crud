package com.dbserver.crud.services;

import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.EnderecoNaoEncontradoException;
import com.dbserver.crud.exceptions.ResourceNotFoundException;
import com.dbserver.crud.repositories.EnderecoRepository;
import com.dbserver.crud.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa adicionaEndereco(Long id, Endereco endereco) throws ResourceNotFoundException {

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o id: " + id));

        endereco.setPessoa(pessoa);
        enderecoRepository.save(endereco);

        pessoa.getEnderecos().add(endereco);
        return pessoaRepository.save(pessoa);
    }

    public List<Endereco> exibeEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> exibeEnderecoPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public Endereco modificaEndereco(Long id, Endereco enderecoAtualizado) throws ResourceNotFoundException {
        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com id: " + id));

        enderecoExistente.setRua(enderecoAtualizado.getRua());
        enderecoExistente.setNumero(enderecoAtualizado.getNumero());
        enderecoExistente.setComplemento(enderecoAtualizado.getComplemento());
        enderecoExistente.setBairro(enderecoAtualizado.getBairro());
        enderecoExistente.setCidade(enderecoAtualizado.getCidade());
        enderecoExistente.setEstado(enderecoAtualizado.getEstado());
        enderecoExistente.setCep(enderecoAtualizado.getCep());

        if (enderecoAtualizado.getPessoa() != null) {
            Pessoa pessoa = pessoaRepository.findById(enderecoAtualizado.getPessoa().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id: " + enderecoAtualizado.getPessoa().getId()));
            enderecoExistente.setPessoa(pessoa);
        }

        return enderecoRepository.save(enderecoExistente);
    }

    public void exclueEndereco(Long id) throws ResourceNotFoundException {
        if (!enderecoRepository.existsById(id)) {
            throw new EnderecoNaoEncontradoException(id);
        }
        enderecoRepository.deleteById(id);
    }
}


