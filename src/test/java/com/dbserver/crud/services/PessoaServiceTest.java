package com.dbserver.crud.services;
import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.UsuarioNaoEncontradoException;
import com.dbserver.crud.repositories.PessoaRepository;

import com.dbserver.crud.exceptions.CpfExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa novaPessoa;
    private LocalDate nascimento;
    private List<Pessoa> pessoas;

    @BeforeEach
    public void setUp() {
        // Configuração comum para todos os testes
        nascimento = LocalDate.of(1968, 10, 4);
        novaPessoa = new Pessoa("Nelson Machado", "000.111.222-33", nascimento);
        novaPessoa.setId(1L);
        pessoas = new ArrayList<>();
        pessoas.add(novaPessoa);
    }

    @Test
    @DisplayName("Deve adicionar uma nova pessoa com sucesso após verificação do Cpf")
    public void adicionaPessoa() {

        // Criação de uma nova pessoa para adicionar  **ARRANGE**
        // Configuração do comportamento esperado do mock do PessoaRepository
        when(pessoaRepository.existsByCpf(novaPessoa.getCpf())).thenReturn(false); // Não existe pessoa com este CPF
        when(pessoaRepository.save(novaPessoa)).thenReturn(novaPessoa); // Retornará a mesma pessoa que foi passada

        // Chama o método a ser testado  **ACT**
        Pessoa pessoaAdicionada = pessoaService.adicionaPessoa(novaPessoa);

        // Verifica se a pessoa retornada é a mesma que foi adicionada **ASSERT**
        assertNotNull(pessoaAdicionada);
        assertEquals(novaPessoa, pessoaAdicionada);
        // Verifica se os métodos do mock foram chamados corretamente
        verify(pessoaRepository, times(1)).existsByCpf(novaPessoa.getCpf());
        verify(pessoaRepository, times(1)).save(novaPessoa);
    }

    @Test
    @DisplayName("Deve lançar a mensagem de exceção após verificar a existencia de Cpf no cadastro")
    public void naoAdicionaPessoaComCpfExistente() {

        when(pessoaRepository.existsByCpf(novaPessoa.getCpf())).thenReturn(true); // Já existe pessoa com este CPF

        assertThrows(CpfExistenteException.class, () -> pessoaService.adicionaPessoa(novaPessoa));
        // Verifica se o método save não foi chamado quando tentamos adicionar uma pessoa com CPF existente
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    @DisplayName("Deve exibir uma lista de pessoas")
    public void exibePessoas(){

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> resultadoPessoas = pessoaService.exibePessoas();

        assertEquals(pessoas, resultadoPessoas);
        verify(pessoaRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deve exibir uma pessoa quando chamada por seu ID com sucesso")
    public void exibePessoaPorId(){

        when(pessoaRepository.findById(novaPessoa.getId())).thenReturn(Optional.of(novaPessoa));

        Optional<Pessoa> pessoaPorId = pessoaService.exibePessoaPorId(novaPessoa.getId());

        assertEquals(novaPessoa, pessoaPorId.get());
        verify(pessoaRepository, times(1)).findById(novaPessoa.getId());

    }

    @Test
    @DisplayName("Deve modificar as informações de pessoa no cadastro com sucesso")
    public void modificaPessoa() throws UsuarioNaoEncontradoException {
        Pessoa pessoaModificada = new Pessoa("Ana Raquel", "555.666.777-88", nascimento);
        pessoaModificada.setId(novaPessoa.getId()); // Certifique-se de que a ID seja a mesma

        when(pessoaRepository.findById(novaPessoa.getId())).thenReturn(Optional.of(novaPessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaModificada);

        Pessoa modificaPessoa = pessoaService.modificaPessoa(pessoaModificada);

        assertEquals(pessoaModificada.getNome(), modificaPessoa.getNome());
        assertEquals(pessoaModificada.getCpf(), modificaPessoa.getCpf());
        assertEquals(pessoaModificada.getNascimento(), modificaPessoa.getNascimento());

        verify(pessoaRepository, times(1)).findById(novaPessoa.getId());
        verify(pessoaRepository, times(1)).save(novaPessoa);
    }

    @Test
    @DisplayName("Deve excluir uma pessoa do cadastro com sucesso")
    public void excluePessoa() {
        // Simulação de uma pessoa encontrada no repositório
        when(pessoaRepository.findById(novaPessoa.getId())).thenReturn(Optional.of(novaPessoa));

        // Verificação de que a exclusão não lança exceção
        assertDoesNotThrow(() -> pessoaService.excluePessoa(novaPessoa.getId()));

        // Verificação das interações com o repositório
        verify(pessoaRepository, times(1)).findById(novaPessoa.getId());
        verify(pessoaRepository, times(1)).delete(novaPessoa);
    }


    @Test
    @DisplayName("Não deve excluir pessoa com ID não identificado ")
    public void naoExcluePessoa(){

        when(pessoaRepository.findById(novaPessoa.getId())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> pessoaService.excluePessoa(novaPessoa.getId()));

        verify(pessoaRepository, times(1)).findById(novaPessoa.getId());
        verify(pessoaRepository, never()).delete(novaPessoa);

    }
}


