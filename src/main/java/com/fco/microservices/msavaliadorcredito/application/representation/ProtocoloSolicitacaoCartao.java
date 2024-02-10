package com.fco.microservices.msavaliadorcredito.application.representation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloSolicitacaoCartao {

    private String protocolo;

    public ProtocoloSolicitacaoCartao(String protocolo) {
        this.protocolo = protocolo;
    }
}
