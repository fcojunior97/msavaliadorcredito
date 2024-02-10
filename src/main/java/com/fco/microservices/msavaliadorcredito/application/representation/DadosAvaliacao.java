package com.fco.microservices.msavaliadorcredito.application.representation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosAvaliacao {
    private String cpf;
    private Long renda;
}
