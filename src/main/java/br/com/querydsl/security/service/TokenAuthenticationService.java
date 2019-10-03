package br.com.querydsl.security.service;

import br.com.querydsl.domain.dto.TokenDTO;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TokenAuthenticationService {

    private static final long EXPIRATIONTIME = 43200000; // 12 horas
    private static final String SECRET = "V86zepSbfBpU6UVqL5hEvEp2JBbud5a52iwlEFMouLs6Qr4oDzDrsyaVEg0RZWS";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public static TokenDTO addAuthentication(Authentication auth, Long userId) {

        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put("roles", auth.getAuthorities());
        claims.put("id", userId);

        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATIONTIME);

        String JWT = Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        String tokenId = TOKEN_PREFIX + " " + JWT;

        TokenDTO token = new TokenDTO();
        token.setToken(tokenId);
        token.setExpirationDate(expirationDate);

        return token;

    }

    public static Authentication getByToken(String token) throws SignatureException {

        try {

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

            List<String> roles = (ArrayList) claims.getBody().get("roles");
            Integer id = (Integer) claims.getBody().get("id");

            String user = claims.getBody().getSubject();

            //Date dataExpiracao = claims.getBody().getExpiration();
            for (Object role : roles) {

                HashMap<String, String> map = (HashMap<String, String>) role;
                List<String> list = new ArrayList<>();
                list.addAll(map.values());

                for (String item : list) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(item));
                }

            }

            if (user != null) {
                UsernamePasswordAuthenticationToken response = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
                response.setDetails(id);

                return response;
            } else {

                return null;
            }

        } catch (SignatureException e) {

            throw e;

        } catch (Exception e) {

            throw e;

        }

    }

    public static Authentication getAuthentication(HttpServletRequest request) throws SignatureException {

        try {

            String token = request.getHeader(HEADER_STRING);

            if (StringUtils.isNotEmpty(token)) {
                return getByToken(token);
            }

            return null;

        } catch (SignatureException e) {

            throw e;

        } catch (Exception e) {

            throw e;
        }

    }

}
