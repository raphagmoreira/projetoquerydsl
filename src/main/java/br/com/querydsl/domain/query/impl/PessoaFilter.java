package br.com.querydsl.domain.query.impl;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.query.Filter;

/**
 * Created by Rapha on 08/08/2019.
 */
public class PessoaFilter implements Filter<Pessoa> {

    private Long id;
    private String nome;

    public PessoaFilter(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
