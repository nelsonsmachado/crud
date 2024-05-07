package com.dbserver.crud.exceptions;


public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long id) {
        super("Não encontramos o usuário com o ID: " + id);
    }
}

