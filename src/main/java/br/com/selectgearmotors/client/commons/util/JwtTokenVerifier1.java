package br.com.selectgearmotors.client.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenVerifier1 {
    public void verifyToken(String token) {
        // Verificar se o token tem 3 partes separadas por "."
        if (token != null && token.split("\\.").length == 3) {
            try {
                DecodedJWT decodedJwt = JWT.decode(token); // Decodifica o token JWT
                System.out.println("Token decodificado com sucesso: " + decodedJwt.getPayload());
            } catch (JWTDecodeException e) {
                System.out.println("Erro ao decodificar o token: " + e.getMessage());
            }
        } else {
            System.out.println("O token não está no formato JWT esperado (3 partes separadas por pontos).");
        }
    }

    public static void main(String[] args) {
        JwtTokenVerifier1 verifier = new JwtTokenVerifier1();

        // Simulação de um token válido (substitua pelo token real)
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        verifier.verifyToken(token); // Verifica e decodifica o token
    }
}
