package br.com.querydsl.domain.entity;

import com.querydsl.core.annotations.QueryProjection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Rapha on 08/08/2019.
 */
@Entity
@SequenceGenerator(allocationSize = 1, name = "seqPessoa", sequenceName = "SEQ_PESSOA")
@Table(name = "PESSOA")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoa")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NOME")
    private String nome;

    public Pessoa() {}

    @QueryProjection
    public Pessoa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Pessoa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
