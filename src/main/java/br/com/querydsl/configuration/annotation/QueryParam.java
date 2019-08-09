package br.com.querydsl.configuration.annotation;

import java.lang.annotation.*;

/**
 * Classe para filtros (end-points) das entidades
 *
 * @author Raphael Moreira.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
}
