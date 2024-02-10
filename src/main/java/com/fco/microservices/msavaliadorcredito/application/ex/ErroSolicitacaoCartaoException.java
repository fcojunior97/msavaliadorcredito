package com.fco.microservices.msavaliadorcredito.application.ex;

public class ErroSolicitacaoCartaoException extends RuntimeException{

    public ErroSolicitacaoCartaoException(String message) {
        super(message);
    }
}
