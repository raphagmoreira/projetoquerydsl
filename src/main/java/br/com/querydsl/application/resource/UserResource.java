package br.com.querydsl.application.resource;

import br.com.querydsl.configuration.annotation.QueryParam;
import br.com.querydsl.domain.dto.TokenDTO;
import br.com.querydsl.domain.entity.User;
import br.com.querydsl.domain.query.Sorter;
import br.com.querydsl.domain.query.impl.UserFilter;
import br.com.querydsl.domain.service.UserService;
import br.com.querydsl.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> find(@QueryParam UserFilter userFilter,
                                           @RequestParam(value = "sortBy", required = false) String sortProperty,
                                           @RequestParam(value = "sortDirection", required = false) Sorter.Direction sortDirection,
                                           @RequestParam(value = "page", required = false) Long page) {

        return ResponseEntity.ok(
                userService.find(userFilter, sortProperty, sortDirection, page)
        );

    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<User>> findPageble(@QueryParam UserFilter userFilter,
                                                  Pageable pageable) {

        Page<User> users = userService.findPage(userFilter, pageable);
        return ResponseEntity.ok(users);

    }

    /*@PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody User user, HttpServletRequest request) throws Exception {
        User userFind = userService.findByUser(user.getUser());

        return ResponseEntity.ok(
                securityService.login(user.getUser(), user.getPassword(), userFind.getId())
        );
    }*/

}
