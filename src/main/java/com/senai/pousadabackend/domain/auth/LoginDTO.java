package com.senai.pousadabackend.domain.auth;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;
    private String password;
}
