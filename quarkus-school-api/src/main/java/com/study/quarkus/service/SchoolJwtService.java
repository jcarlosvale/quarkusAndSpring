package com.study.quarkus.service;

import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchoolJwtService {

    public String generateJwt() {
        return Jwt
                .issuer("school-jwt")
                .subject("school-jwt")
                .groups("Professor")
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
