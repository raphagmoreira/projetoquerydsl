package br.com.querydsl.domain.repository;

import br.com.querydsl.domain.entity.Pessoa;

import java.util.List;

/**
 * Repositório da entidade Pessoa
 *
 * @author Raphael Moreira
 */
public interface PessoaRepository extends Repository<Pessoa, Long> {

    List<Pessoa> findPessoasSubQuery();

    List<Pessoa> findPessoasUnion();

}
