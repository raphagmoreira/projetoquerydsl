package br.com.querydsl.domain.service;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.entity.User;
import br.com.querydsl.domain.exception.EntityNotFoundException;
import br.com.querydsl.domain.query.Query;
import br.com.querydsl.domain.query.Sorter;
import br.com.querydsl.domain.query.impl.PessoaFilter;
import br.com.querydsl.domain.query.impl.UserFilter;
import br.com.querydsl.domain.repository.PessoaRepository;
import br.com.querydsl.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rapha on 08/08/2019.
 */
@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    public List<User> find(UserFilter userFilter,
                             String sortProperty,
                             Sorter.Direction sortDirection,
                             Long page) {
        //Constrói a query para a entidade User
        final Query<User> query = Query.<User>builder()
                .filter(userFilter)
                .sort(Sorter.<User>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();

        return userRepository.find(query);
    }

    public Page<User> findPage(UserFilter userFilter,
                                 Pageable pageable) {
        //Constrói a query para a entidade User
        final Query<User> query = Query.<User>builder()
                .filter(userFilter)
                .limit(Long.valueOf(pageable.getPageSize()))
                .page(Long.valueOf(pageable.getOffset()))
                .build();

        return new PageImpl<>(userRepository.find(query), pageable, userRepository.count(query));
    }

    public User findByUser(String user) {
        return userRepository.findByUser(user);
    }

    @Transactional
    public void create(User user) {
        userRepository.create(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.update(user);
    }

    @Transactional
    public void remove(Long idUser) {
        userRepository.removeById(idUser);
    }
}
