package com.example.Intesis.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.Intesis.Entity.User.User;
import com.example.Intesis.Entity.User.UserRole;
import org.antlr.v4.runtime.Token;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class TokenServiceTest {

    private TokenService tokenService = new TokenService();

    @Test
    void tokenGerado() {
        UUID idUser = UUID.randomUUID();

        User newUser = new User();
        newUser.setId(idUser);
        newUser.setLogin("login");
        newUser.setPassword("1234");
        newUser.setRole(UserRole.ADMINISTRADOR);

        String token = tokenService.tokenGerado(newUser);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void verificarToken() {
        UUID idUser = UUID.randomUUID();

        User newUser = new User();
        newUser.setId(idUser);
        newUser.setLogin("login");
        newUser.setPassword("1234");
        newUser.setRole(UserRole.ADMINISTRADOR);

        String token = tokenService.tokenGerado(newUser);

        String response = tokenService.verificarToken(token);

        assertNotNull(response);
        assertEquals("login", response);
    }

    @Test
    void verificarTokenInvalido() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpbnRlc2lzIiwic3ViIjoiZmVsaXBlIiwiZXhwIjoxNzQ1NzAzNjYyfQ.ondhCa635xIsY7QqJ6dgiQQLemks-97rCHW5fWQDa5Y";
        String response = tokenService.verificarToken(token);

        assertNotNull(response);
        assertEquals("Não Foi Possivel Verificar O Token", response);
    }
}