package com.fco.microservices.msavaliadorcredito.infra.clients;

import com.fco.microservices.msavaliadorcredito.application.representation.Cartao;
import com.fco.microservices.msavaliadorcredito.application.representation.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> buscaCartoesPorCliente(@RequestParam("cpf") String cpf);

    @GetMapping
    public ResponseEntity<List<Cartao>> buscaCartoesRenda(@RequestParam Long renda);

}
