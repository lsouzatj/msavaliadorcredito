package com.br.msavaliadorcredito.services;

import com.br.msavaliadorcredito.entities.SituacaoCliente;
import com.br.msavaliadorcredito.exceptions.ComunicacaoServiceException;

public interface AvaliadorCreditoService {
    SituacaoCliente obterSituacaoCliente(String cpf) throws ComunicacaoServiceException;
}
