package br.com.querydsl.security.filter;

import br.com.querydsl.domain.enums.ErrorsEnum;
import br.com.querydsl.security.service.TokenAuthenticationService;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;

        try {

            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (SignatureException e) {

            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorsEnum.USER_INVALID_TOKEN.value);
            filterChain.doFilter(request, response);
            return;

        }

        filterChain.doFilter(request, response);

    }

}
