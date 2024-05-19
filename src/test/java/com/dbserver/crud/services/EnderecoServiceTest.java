package com.dbserver.crud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.repositories.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco novoEndereco;
    private List<Endereco> enderecos;

    @BeforeEach
    void setUp() {
        novoEndereco = new Endereco();
        novoEndereco.setId(1L);
        enderecos = new ArrayList<>();
        enderecos.add(novoEndereco);
    }

    @Test
    @DisplayName("Deve exibir uma lista de endereços com sucesso")
    public void exibeEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(enderecos);

        List<Endereco> listaEnderecos = enderecoService.exibeEnderecos();

        assertEquals(enderecos, listaEnderecos);
        verify(enderecoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve exibir um endereço quando chamado por seu ID com sucesso")
    public void exibeEnderecoPorId() {
        when(enderecoRepository.findById(novoEndereco.getId())).thenReturn(Optional.of(novoEndereco));

        Optional<Endereco> enderecoPorId = enderecoService.exibeEnderecoPorId(novoEndereco.getId());

        assertEquals(novoEndereco, enderecoPorId.get());
        verify(enderecoRepository, times(1)).findById(novoEndereco.getId());
    }
}
