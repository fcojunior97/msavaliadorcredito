package com.fco.microservices.msavaliadorcredito.application.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
    List<CartaoAprovado> cartoes;
}
