package com.dbserver.crud.exceptions;

public class CpfExistenteException extends RuntimeException {
    public CpfExistenteException(String mensagem) {
        super(mensagem);
    }
}

