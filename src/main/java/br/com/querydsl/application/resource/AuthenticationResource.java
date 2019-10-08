package br.com.querydsl.application.resource;

import br.com.querydsl.domain.dto.TokenDTO;
import br.com.querydsl.domain.entity.Authentication;
import br.com.querydsl.domain.entity.User;
import br.com.querydsl.domain.service.UserService;
import br.com.querydsl.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationResource {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> authentication(@RequestBody Authentication authentication) throws Exception {
        User userFind = userService.findByUser(authentication.getUsername());

        return ResponseEntity.ok(
                securityService.login(authentication.getUsername(), authentication.getPassword(), userFind.getId())
        );
    }

}
