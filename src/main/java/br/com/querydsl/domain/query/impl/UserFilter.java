package br.com.querydsl.domain.query.impl;

import br.com.querydsl.domain.entity.User;
import br.com.querydsl.domain.query.Filter;

/**
 * Created by Rapha on 08/08/2019.
 */
public class UserFilter implements Filter<User> {

    private Long id;
    private String user;

    public UserFilter(Long id, String user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }
}
