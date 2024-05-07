package com.dbserver.crud.controller;

import com.dbserver.crud.entities.Pessoa;
import com.dbserver.crud.exceptions.CpfExistenteException;
import com.dbserver.crud.exceptions.UsuarioNaoEncontradoException;
import com.dbserver.crud.repositories.PessoaRepository;
import com.dbserver.crud.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/insere")
    public ResponseEntity<Pessoa> adicionaPessoa(@RequestBody Pessoa novaPessoa) {
        Pessoa pessoa = pessoaService.adicionaPessoa(novaPessoa);
        return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
    }

    @ExceptionHandler(CpfExistenteException.class)
    public ResponseEntity<String> handleCpfExistenteException(CpfExistenteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Pessoa>> exibePessoas() {
        List<Pessoa> pessoas = pessoaService.exibePessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> exibePessoaPorId(@PathVariable Long id) {
        Optional<Pessoa> pessoaPorId = pessoaService.exibePessoaPorId(id);
        return pessoaPorId.map(pessoa -> new ResponseEntity<>(pessoa, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluePessoa(@PathVariable Long id) {
        try {
            pessoaService.excluePessoa(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsuarioNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
