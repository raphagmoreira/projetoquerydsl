package br.com.querydsl.infra.persistence.jpa.repository;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.entity.QUser;
import br.com.querydsl.domain.entity.User;
import br.com.querydsl.domain.query.Query;
import br.com.querydsl.domain.query.impl.UserFilter;
import br.com.querydsl.domain.repository.UserRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Repositório da entidade User
 *
 * @author Raphael Moreira
 */
@Repository
public class UserJpaRepository extends AbstractJpaRepository<User, Long> implements UserRepository {
    @Override
    public List<User> find(Query<User> query) {
        return this.createQuery(query, Boolean.FALSE).fetch();
    }

    private JPAQuery<User> createQuery(Query<User> query, Boolean sortAndPaging) {
        QUser qUser = QUser.user1;
        final PathBuilder<Pessoa> path = new PathBuilder<>(Pessoa.class, "user");

        final JPAQuery<User> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QUser.create(
                                qUser.id,
                                qUser.user
                        )
                )
                .from(qUser);

        if (Objects.nonNull(query)) {
            if (query.getFilter() != null && query.getFilter() instanceof UserFilter) {
                final UserFilter filter = (UserFilter) query.getFilter();

                if (Objects.nonNull(filter.getId())) {
                    jpaQuery.where(qUser.id.eq(filter.getId()));
                }

                if (Objects.nonNull(filter.getUser())) {
                    jpaQuery.where(qUser.user.eq(filter.getUser()));
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

            jpaQuery.offset(query.getPage());

            // limit
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery;
    }

    @Override
    public long count(Query<User> query) {
        //Utilizado para paginar
        return this.createQuery(query, Boolean.FALSE).fetchCount();
    }

    @Override
    public User findByUser(String user) {
        QUser qUser = QUser.user1;

        return new JPAQueryFactory(entityManager)
                .select(
                        qUser
                )
                .from(qUser)
                .where(qUser.user.eq(user))
                .fetchOne();
    }
}
