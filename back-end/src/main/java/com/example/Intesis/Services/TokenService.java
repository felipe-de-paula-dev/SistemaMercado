package com.example.Intesis.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.Intesis.Entity.User.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private Instant gerarTempoDeExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


    public String tokenGerado(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256("intesis");
            String token = JWT.create()
                    .withIssuer("intesis")
                    .withSubject(user.getLogin())
                    .withExpiresAt(gerarTempoDeExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro Para Gerar Token ", exception);
        }
    }

    public String verificarToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("intesis");
            return JWT.require(algorithm)
                    .withIssuer("intesis")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException exception) {
           return "";
        } catch (JWTVerificationException exception) {
            return "Não Foi Possivel Verificar O Token";
        }
    }


}
