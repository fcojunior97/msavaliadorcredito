package com.fco.microservices.msavaliadorcredito.application.service;

import com.fco.microservices.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.fco.microservices.msavaliadorcredito.application.ex.ErroComunicao;
import com.fco.microservices.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import com.fco.microservices.msavaliadorcredito.application.representation.*;
import com.fco.microservices.msavaliadorcredito.domain.model.SituacaoCliente;
import com.fco.microservices.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.fco.microservices.msavaliadorcredito.infra.clients.ClienteResourceClient;
import com.fco.microservices.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient clienteResourceClient;

    @Autowired
    private CartoesResourceClient cartoesResourceClient;

    @Autowired
    private SolicitacaoEmissaoCartaoPublisher solicitacaoEmissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicao {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.buscar(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartoesResponse = cartoesResourceClient.buscaCartoesPorCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(dadosCartoesResponse.getBody())
                    .build();

        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicao(e.getMessage(), status);
        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicao {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.buscar(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.buscaCartoesRenda(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();

           var listaCartoesAprovados =  cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setCartao(cartao.getNome());
                cartaoAprovado.setBandeira(cartao.getBandeira());
                cartaoAprovado.setLimiteAprovado(limiteAprovado);

                return cartaoAprovado;
            }).collect(Collectors.toList());

           return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicao(e.getMessage(), status);
        }

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {

        try {
            solicitacaoEmissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();

            return new ProtocoloSolicitacaoCartao(protocolo);

        }catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }


}
