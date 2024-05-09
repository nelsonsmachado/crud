package com.dbserver.crud.services;
import com.dbserver.crud.entities.Pessoa;
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

    @BeforeEach
    public void setUp() {
        // Configuração comum para todos os testes
        nascimento = LocalDate.of(1968, 10, 4);
        novaPessoa = new Pessoa("Nelson Machado", "000.111.222-33", nascimento);
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
        Pessoa pessoaExistente = new Pessoa("Nelson Machado", "000.111.222-33", nascimento);

        when(pessoaRepository.existsByCpf(pessoaExistente.getCpf())).thenReturn(true); // Já existe pessoa com este CPF

        assertThrows(CpfExistenteException.class, () -> pessoaService.adicionaPessoa(pessoaExistente));
        // Verifica se o método save não foi chamado quando tentamos adicionar uma pessoa com CPF existente
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }
}


