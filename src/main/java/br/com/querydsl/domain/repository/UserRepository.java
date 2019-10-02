package br.com.querydsl.domain.repository;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.entity.User;

/**
 * Repositório da entidade User
 *
 * @author Raphael Moreira
 */
public interface UserRepository extends Repository<User, Long> {

    User findByUser(String user);

}
