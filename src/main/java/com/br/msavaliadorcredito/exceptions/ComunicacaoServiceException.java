package com.br.msavaliadorcredito.exceptions;

import lombok.Getter;

public class ComunicacaoServiceException extends  Exception{

    @Getter
    private int status;

    public ComunicacaoServiceException(String msg) {
        super(msg);
    }
    public ComunicacaoServiceException(String msg, int status) {
        super(msg);
        this.status = status;
    }
}
