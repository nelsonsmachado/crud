package com.dbserver.crud.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(Long id) {
        super("Endereço não encontrado com o ID: " + id);
    }
}

