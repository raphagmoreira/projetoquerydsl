package br.com.querydsl.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Tratamento de Exceções Genéricas
 *
 * @author Raphael Moreira
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GenericErrorException extends RuntimeException {
    public GenericErrorException(String mensagem) {
        super(mensagem);
    }
}
