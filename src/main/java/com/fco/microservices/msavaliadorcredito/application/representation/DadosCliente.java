package com.fco.microservices.msavaliadorcredito.application.representation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosCliente {

    private Long id;
    private String nome;
    private Integer idade;
}
