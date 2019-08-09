package br.com.querydsl.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @autor Raphael Moreira.
 *
 * Classe padrão para tratamento de erros IdNull
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IdNullException extends RuntimeException {
    public IdNullException() {
        super("Campo ID está vazio!");
    }
}
