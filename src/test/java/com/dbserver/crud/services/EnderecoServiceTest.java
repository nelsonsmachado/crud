package com.dbserver.crud.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.dbserver.crud.entities.Endereco;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.EnderecoNaoEncontradoException;
import com.dbserver.crud.exceptions.UsuarioNaoEncontradoException;
import com.dbserver.crud.repositories.EnderecoRepository;
import com.dbserver.crud.repositories.PessoaRepository;
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

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco novoEndereco;
    private List<Endereco> enderecos;
    private LocalDate nascimento;
    private Pessoa novaPessoa;
    private Long idInvalido;

    @BeforeEach
    void setUp() {
        novoEndereco = new Endereco();
        novoEndereco.setId(1L);
        enderecos = new ArrayList<>();
        enderecos.add(novoEndereco);
        nascimento = LocalDate.of(1968, 10, 4);
        novaPessoa = new Pessoa("Nelson Machado", "000.111.222-33", nascimento);
        novaPessoa.setId(1L);
        idInvalido = 100L;
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

    @Test
    @DisplayName("Deve adicionar um endereço com sucesso")
    public void adicionaEndereco(){

        novaPessoa.addEndereco(novoEndereco);
        novaPessoa.setId(3L);
        novoEndereco.setPessoa(novaPessoa);

        when(pessoaRepository.findById(novaPessoa.getId())).thenReturn(Optional.of(novaPessoa));
        when(enderecoRepository.save(novoEndereco)).thenReturn(novoEndereco);
        when(pessoaRepository.save(novaPessoa)).thenReturn(novaPessoa);

        assertDoesNotThrow(()-> enderecoService.adicionaEndereco(novaPessoa.getId(), novoEndereco));

        verify(pessoaRepository, times(1)).findById(novaPessoa.getId());
        verify(enderecoRepository, times(1)).save(novoEndereco);
        verify(pessoaRepository, times(1)).save(novaPessoa);

    }

//    @Test
//    @DisplayName("Deve apresentar erro ao tentar adicionar um novo endereço")
//    public void naoAdicionaEndereco(){
//
//        when(pessoaRepository.findById(idInvalido)).thenReturn(Optional.empty());
//
//        assertThrows(UsuarioNaoEncontradoException.class, ()-> enderecoService.adicionaEndereco(idInvalido, novoEndereco));
//
//        verify(pessoaRepository, times(1)).findById(idInvalido);
//        verify(enderecoRepository, never()).save(any());
//        verify(pessoaRepository, never()).save(any());
//
//    }

    @Test
    @DisplayName("Deve deletar um endereco com sucesso")
    public void exclueEndereco(){

        when(enderecoRepository.existsById(novoEndereco.getId())).thenReturn(true);


        assertDoesNotThrow(()-> enderecoService.exclueEndereco(novoEndereco.getId()));

        verify(enderecoRepository, times(1)).existsById(novoEndereco.getId());
        verify(enderecoRepository, times(1)).deleteById(novoEndereco.getId());

    }

    @Test
    @DisplayName("Não deve excluir pessoa com ID não identificado ")
    public void naoExclueEndereco(){

    when(enderecoRepository.existsById(novoEndereco.getId())).thenReturn(false);

    assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.exclueEndereco(novoEndereco.getId()));

    verify(enderecoRepository, times(1)).existsById(novoEndereco.getId());
    verify(enderecoRepository, never()).delete(novoEndereco);

    }
}
