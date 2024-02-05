package com.fco.microservices.msavaliadorcredito.application.resource;

import com.fco.microservices.msavaliadorcredito.application.service.AvaliadorCreditoService;
import com.fco.microservices.msavaliadorcredito.domain.model.SituacaoCliente;
import com.fco.microservices.msavaliadorcredito.infra.clients.CartoesResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-credito")
public class AvaliadorCreditoResource {

    @Autowired
    private AvaliadorCreditoService avaliadorCreditoService;


    @GetMapping
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam String cpf) {
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);
    }
}
