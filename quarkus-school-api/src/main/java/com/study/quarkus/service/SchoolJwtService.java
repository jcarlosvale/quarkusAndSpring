package com.study.quarkus.service;

import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class SchoolJwtService {

    public String generateJwt() {
        return Jwt
                .issuer("school-jwt")
                .subject("school-jwt")
                .groups(Set.of("Professor", "Aluno"))
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
