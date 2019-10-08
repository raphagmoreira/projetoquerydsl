package br.com.querydsl.domain.entity;

import javax.validation.constraints.Size;
import java.io.Serializable;


public class Authentication implements Serializable {

    @Size(min = 1, max = 30)
    private String username;

    @Size(min = 1, max = 10)
    private String password;

    public Authentication() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
