package com.fco.microservices.msavaliadorcredito.domain.model;

import com.fco.microservices.msavaliadorcredito.application.resource.representation.CartaoCliente;
import com.fco.microservices.msavaliadorcredito.application.resource.representation.DadosCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoCliente {

    private DadosCliente cliente;
    private List<CartaoCliente> cartoes;
}
