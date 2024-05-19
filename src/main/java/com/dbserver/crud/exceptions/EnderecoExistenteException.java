package com.dbserver.crud.exceptions;

public class EnderecoExistenteException extends RuntimeException {
    public EnderecoExistenteException(String mensagem) {
        super(mensagem);
    }
}

