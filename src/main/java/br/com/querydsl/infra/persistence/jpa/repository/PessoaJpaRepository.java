package br.com.querydsl.infra.persistence.jpa.repository;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.entity.QPessoa;
import br.com.querydsl.domain.query.Query;
import br.com.querydsl.domain.query.impl.PessoaFilter;
import br.com.querydsl.domain.repository.PessoaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repositório da entidade Pessoa
 *
 * @author Raphael Moreira
 */
@Repository
public class PessoaJpaRepository extends AbstractJpaRepository<Pessoa, Long> implements PessoaRepository {
    @Override
    public List<Pessoa> find(Query<Pessoa> query) {
        return this.createQuery(query, Boolean.FALSE).fetch();
    }

    private JPAQuery<Pessoa> createQuery(Query<Pessoa> query, Boolean sortAndPaging) {
        QPessoa qPessoa = QPessoa.pessoa;
        final PathBuilder<Pessoa> path = new PathBuilder<>(Pessoa.class, "pessoa");

        final JPAQuery<Pessoa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nome
                        )
                )
                .from(qPessoa);

        if (Objects.nonNull(query)) {
            if (query.getFilter() != null && query.getFilter() instanceof PessoaFilter) {
                final PessoaFilter filter = (PessoaFilter) query.getFilter();

                if (Objects.nonNull(filter.getId())) {
                    jpaQuery.where(qPessoa.id.eq(filter.getId()));
                }

                if (Objects.nonNull(filter.getNome())) {
                    jpaQuery.where(qPessoa.nome.likeIgnoreCase("%" + filter.getNome() + "%"));
                }
            }
        }

        //Utilizado para paginar
        if (sortAndPaging) {
            if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
                if (query.getSorter().isDesc()) {
                    jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
                } else {
                    jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
                }
            } else {
                jpaQuery.orderBy(path.getString("id").asc());
            }

            // page
            jpaQuery.offset(query.getPage());

            // limit
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery;
    }

    @Override
    public long count(Query<Pessoa> query) {
        //Utilizado para paginar
        return this.createQuery(query, Boolean.FALSE).fetchCount();
    }

    @Override
    public List<Pessoa> findPessoasSubQuery() {
        QPessoa qPessoa = QPessoa.pessoa;

        //Para utilizar o mesmo objeto na query, e preciso instanciar passando um nome diferente no construtor
        QPessoa qPessoa1 = new QPessoa("pessoaID");
        QPessoa qPessoa2 = new QPessoa("pessoaNome");

        return new JPAQueryFactory(entityManager)
                .select(
                        QPessoa.create(
                                JPAExpressions
                                        .select(
                                                qPessoa1.id
                                        )
                                        .from(qPessoa1)
                                        .where(qPessoa1.eq(qPessoa)),
                                JPAExpressions
                                        .select(
                                                qPessoa2.nome
                                        )
                                        .from(qPessoa2)
                                        .where(qPessoa2.eq(qPessoa))
                        )
                )
                .from(qPessoa)
                .fetch();
    }

    @Override
    public List<Pessoa> findPessoasUnion() {
        QPessoa qPessoa = QPessoa.pessoa;
        QPessoa qPessoa1 = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nome
                        )
                )
                .from(qPessoa)
                .fetch();
    }
}
