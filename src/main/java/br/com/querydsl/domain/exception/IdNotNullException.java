package br.com.querydsl.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @autor Raphael Moreira.
 *
 * Classe padrão para tratamento de erros IdNotNull
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IdNotNullException extends RuntimeException {
    public IdNotNullException() {
        super("Já existe um registro com esse ID!");
    }
}
