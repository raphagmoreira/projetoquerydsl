package br.com.querydsl.security.service.impl;

import br.com.querydsl.domain.exception.UserNotFoundException;
import br.com.querydsl.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UserNotFoundException {
        try {
            br.com.querydsl.domain.entity.User user = userRepository.findByUser(login);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            //grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

            return User.withUsername(user.getUser()).password(user.getPassword()).authorities(grantedAuthorities).build();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException();
        }
    }

}