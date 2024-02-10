package com.fco.microservices.msavaliadorcredito.application.resource;

import com.fco.microservices.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.fco.microservices.msavaliadorcredito.application.ex.ErroComunicao;
import com.fco.microservices.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import com.fco.microservices.msavaliadorcredito.application.representation.DadosAvaliacao;
import com.fco.microservices.msavaliadorcredito.application.representation.DadosSolicitacaoEmissaoCartao;
import com.fco.microservices.msavaliadorcredito.application.representation.ProtocoloSolicitacaoCartao;
import com.fco.microservices.msavaliadorcredito.application.representation.RetornoAvaliacaoCliente;
import com.fco.microservices.msavaliadorcredito.application.service.AvaliadorCreditoService;
import com.fco.microservices.msavaliadorcredito.domain.model.SituacaoCliente;
import com.fco.microservices.msavaliadorcredito.infra.clients.CartoesResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
public class AvaliadorCreditoResource {

    @Autowired
    private AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public ResponseEntity consultaSituacaoCliente(@RequestParam String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicao e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity avaliacaoCredito(@RequestBody DadosAvaliacao dados) {
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicao e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao (@RequestBody DadosSolicitacaoEmissaoCartao dados) {

        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        }catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
