package br.com.querydsl.security.service.impl;

import br.com.querydsl.domain.dto.TokenDTO;
import br.com.querydsl.domain.exception.UserNotFoundException;
import br.com.querydsl.security.service.SecurityService;
import br.com.querydsl.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;

@Service
@Component
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public TokenDTO login(String username, String password, Long userId) throws Exception {

        try {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (Objects.nonNull(userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities());

                Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

                if (auth.isAuthenticated()) {
                    return TokenAuthenticationService.addAuthentication(auth, userId);
                } else {
                    throw new AuthenticationException();
                }

            } else {
                throw new UserNotFoundException();
            }

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

}
