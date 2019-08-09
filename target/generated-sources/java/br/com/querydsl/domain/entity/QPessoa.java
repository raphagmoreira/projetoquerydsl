package br.com.querydsl.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.Expression;


/**
 * QPessoa is a Querydsl query type for Pessoa
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPessoa extends EntityPathBase<Pessoa> {

    private static final long serialVersionUID = 276891414L;

    public static ConstructorExpression<Pessoa> create(Expression<Long> id) {
        return Projections.constructor(Pessoa.class, new Class<?>[]{long.class}, id);
    }

    public static ConstructorExpression<Pessoa> create(Expression<Long> id, Expression<String> nome) {
        return Projections.constructor(Pessoa.class, new Class<?>[]{long.class, String.class}, id, nome);
    }

    public static final QPessoa pessoa = new QPessoa("pessoa");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nome = createString("nome");

    public QPessoa(String variable) {
        super(Pessoa.class, forVariable(variable));
    }

    public QPessoa(Path<? extends Pessoa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPessoa(PathMetadata metadata) {
        super(Pessoa.class, metadata);
    }

}

