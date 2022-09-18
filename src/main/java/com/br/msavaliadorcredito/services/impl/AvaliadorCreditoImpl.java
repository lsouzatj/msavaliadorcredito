package com.br.msavaliadorcredito.services.impl;


import com.br.msavaliadorcredito.clients.CartoesResourceClient;
import com.br.msavaliadorcredito.clients.ClienteResourceClient;
import com.br.msavaliadorcredito.dtos.CartaoClienteDTO;
import com.br.msavaliadorcredito.dtos.DadosClienteDTO;
import com.br.msavaliadorcredito.entities.SituacaoCliente;
import com.br.msavaliadorcredito.exceptions.ComunicacaoServiceException;
import com.br.msavaliadorcredito.services.AvaliadorCreditoService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoImpl implements AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;
    @Override
    public SituacaoCliente obterSituacaoCliente(String cpf) throws ComunicacaoServiceException {
        try {
            //Obter dados do cliente - MsClientes
            ResponseEntity<DadosClienteDTO> dadosClienteResponse = clienteResourceClient.consultarDadosCliente(cpf);
            //Obter cartoes do cliente - MsCartoes
            ResponseEntity<List<CartaoClienteDTO>> cartoesClienteResponse = cartoesResourceClient.getCartoesByCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .dadosClienteDTO(dadosClienteResponse.getBody())
                    .cartaoClienteDTO(cartoesClienteResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            var status = e.status();
            var msg = e.getMessage();
            throw new ComunicacaoServiceException(msg, status);
        }catch (FeignException.ServiceUnavailable e){
            var status = e.status();
            var msg = e.getMessage();
            throw new ComunicacaoServiceException("Servico indisponivel", status);
        }
    }
}
