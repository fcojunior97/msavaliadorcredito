package com.fco.microservices.msavaliadorcredito.application.service;

import com.fco.microservices.msavaliadorcredito.application.resource.representation.CartaoCliente;
import com.fco.microservices.msavaliadorcredito.application.resource.representation.DadosCliente;
import com.fco.microservices.msavaliadorcredito.domain.model.SituacaoCliente;
import com.fco.microservices.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.fco.microservices.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient clienteResourceClient;

    @Autowired
    private CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.buscar(cpf);
        ResponseEntity<List<CartaoCliente>> dadosCartoesResponse = cartoesResourceClient.buscaCartoesPorCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(dadosCartoesResponse.getBody())
                .build();
    }
}
