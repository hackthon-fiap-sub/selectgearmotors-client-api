package br.com.selectgearmotors.client.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwtDecoder {
    public static void main(String[] args) {
        String token = "eyJraWQiOiJ4Y2pqbUVqS2dCcTFydkczRWNXOFAzQ3JkS0R5U0ZLT0huYnBEVjV3eCtnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiQlBUb0djTmZxRmFabWNKc1BpNXVUZyIsInN1YiI6IjM0YTgzNDU4LWQwZDEtNzBlZS04YmY0LWJlMjRlMjZhMjkxNCIsImNvZ25pdG86Z3JvdXBzIjpbImFkbWluIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX05iMG02MG5raiIsImN1c3RvbTppZCI6IjcwZDU3ZTFhLTMwYjItNDA3ZC1hNzVjLThmZjc2NDNjODQ2MCIsImNvZ25pdG86dXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIiwiYXVkIjoiNXFobGhjOW1wZzIwNTFhcjdzMTBqM3J1cSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNzI2Njg4MTQ1LCJuYW1lIjoiSGVpdG9yIEJpdHRlbmNvdXJ0IGRlIEF6ZXZlZG8iLCJleHAiOjE3MjY2OTE3NDUsImlhdCI6MTcyNjY4ODE0NSwianRpIjoiZmUyZTg4OWMtOTE1OS00YzExLTg0YjYtNDZlZWZlNTA2NzFjIiwiZW1haWwiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciJ9.h-tux2x_utEv3nRYRNEJOXD5lkL6NKphRDKxqbMKDVaZwkF98tNmUKry4tpFhlX4Qzsgcy627pRP6L9WGUwsCcYaDMDoYF4NMmWWdBwX9RlFufMCkiG9XZyCisFu3Pg_O_nNmSR5X4q_zuoh2GR7iyE1LtFZeuLP-onNbI-XbYiswdQFzuF12nV-rl8k8_Qwae5wBKGxCi0-2y_U3LzVui4HArIJMJvMMDbJ8zdvuNM0UJR4pQonrXo6j6Vy1PLpZQmjZuxwVglkx06-stNVD3GihaPrUBs5YUsTKXnA9X6y-FF7u_9LFnevWblu4sQQQq7_xO4eZJyEnbhW9cpoag&access_token=eyJraWQiOiJJaEtlOE5LQURwbFZDM3NOa3gxa2NwWldFS3FaNExiVktubDRrZm9DdmVnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNGE4MzQ1OC1kMGQxLTcwZWUtOGJmNC1iZTI0ZTI2YTI5MTQiLCJjb2duaXRvOmdyb3VwcyI6WyJhZG1pbiJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9OYjBtNjBua2oiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1cWhsaGM5bXBnMjA1MWFyN3MxMGozcnVxIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJhd3MuY29nbml0by5zaWduaW4udXNlci5hZG1pbiBvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImF1dGhfdGltZSI6MTcyNjY4ODE0NSwiZXhwIjoxNzI2NjkxNzQ1LCJpYXQiOjE3MjY2ODgxNDUsImp0aSI6IjI4ZGEyMWQ3LTFhMDktNDVlMi04OTNmLWMwMzAxOGVlNDdkZSIsInVzZXJuYW1lIjoiaGVpdG9yLmJpdHRlbmNvdXJ0LmF6ZXZlZG9Ac2VsZWN0Z2Vhcm1vdG9ycy5jb20uYnIifQ.Xm6cjwNAb2nsZjBaKgCcVx-_-Ej0OPucTqIkBxSRvnuBGNcAXl7hPnqx62UNE5eeNU2lHC4r-M4IC9XjSmf1zbM330E7jPlhkSLzy3dzeHqkAj2fwNNVvdpyLZr7-is5RvZrIJdlFzUUvMR3OK50coSQjLqUBuO_z9UK05CRHAVORtVCC0W4ft5UQghAfJBAnOBDjaJMpJD6-kJldyIbt3_jeRw6MQrfceAEV7BzipEIw4YMA88ireJr1SywG75wmHhpjK9VHw42vLmiGI3NA_2g0J5nxrG8r2eyenFAMC5UbQPZYuMREAFaTt76jIQJr7W3PmBpM2QfOTDuShFokQ";

        try {

            // Dividir o token JWT nas três partes (header, payload, signature)
            String[] tokenParts = token.split("\\.");

            if (tokenParts.length != 3) {
                throw new IllegalArgumentException("Token JWT inválido. Deve conter header, payload, e signature.");
            }

            // Decodificar header e payload (signature não é decodificada, pois é apenas uma verificação de integridade)
            String header = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
            String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]));

            // Exibir o conteúdo decodificado
            System.out.println("Header: " + header);
            System.out.println("Payload: " + payload);

            // Substitua por sua chave pública RSA em formato PEM
            String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\nMIIBIjANB...";
            publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));

            // Configura o algoritmo para usar a chave pública RSA
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // Verifica e valida o JWT
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            System.out.println("Token válido. Claim 'sub': " + decodedJWT.getSubject());
        } catch (JWTVerificationException exception) {
            System.out.println("Erro ao verificar o token: " + exception.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
