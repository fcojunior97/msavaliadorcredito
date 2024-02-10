package com.fco.microservices.msavaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicao extends Exception{

    @Getter
    private Integer status;

    public ErroComunicao(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
