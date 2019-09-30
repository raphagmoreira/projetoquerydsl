package br.com.querydsl.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @autor Raphael Moreira.
 *
 * Classe padrão para tratamento de erros EntityNotFound
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Não foram encontrados registros!");
    }
}
