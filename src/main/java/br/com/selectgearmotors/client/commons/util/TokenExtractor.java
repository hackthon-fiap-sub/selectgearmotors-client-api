package br.com.selectgearmotors.client.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class TokenExtractor {

    public static void main(String[] args) {
        // URL completa com os tokens
        String url = "https://selectgearsmotors.auth.us-east-1.amazoncognito.com/callback#id_token=eyJraWQiOiJ4Y2pqbUVqS2dCcTFydkczRWNXOFAzQ3JkS0R5U0ZLT0huYnBEVjV3eCtnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiZjc0U2FVODVwa3RzVTZQaU1FLTJlZyIsInN1YiI6IjM0YTgzNDU4LWQwZDEtNzBlZS04YmY0LWJlMjRlMjZhMjkxNCIsImNvZ25pdG86Z3JvdXBzIjpbImFkbWluIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX05iMG02MG5raiIsImN1c3RvbTppZCI6IjcwZDU3ZTFhLTMwYjItNDA3ZC1hNzVjLThmZjc2NDNjODQ2MCIsImNvZ25pdG86dXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIiwiYXVkIjoiNXFobGhjOW1wZzIwNTFhcjdzMTBqM3J1cSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNzI2NzExMTYwLCJuYW1lIjoiSGVpdG9yIEJpdHRlbmNvdXJ0IGRlIEF6ZXZlZG8iLCJleHAiOjE3MjY3MTQ3NjAsImlhdCI6MTcyNjcxMTE2MCwianRpIjoiNjVhODU2OTgtYTQ0YS00NGM1LWJkNzAtOGFhYTVhYTNmOTE3IiwiZW1haWwiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciJ9.bzIrjRqUU-GCZJGj7Jl5IKD9ioe-L9a6xSVCeonH5TfikXoGXkfFw-HAdE28SY0agoc8w5FWr39B-BiUCw50Q7H_WScEtpbOiFyaobcFlkSEiNz40gd3LWrgcorwrYN-uw5tBMXtY0zNjJB1ITRGKqg3HP4tsWEGKG9bSLUmYpM9xUemN6VAFU2F1MNg1nQemAI4ajpQOnbD-PevvWWt5zNOwev1l0WRqeGVFlhhmSysxBB6Tgao79hOCmjvP6ygJEIPawveFQ95R5odmaafd1K1Nw_jWqpWn605TDETpNpWzAThQlFDu0gzjaxBLdY8eP1eXI5oTMQwuO3N7peMFg&access_token=eyJraWQiOiJJaEtlOE5LQURwbFZDM3NOa3gxa2NwWldFS3FaNExiVktubDRrZm9DdmVnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNGE4MzQ1OC1kMGQxLTcwZWUtOGJmNC1iZTI0ZTI2YTI5MTQiLCJjb2duaXRvOmdyb3VwcyI6WyJhZG1pbiJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9OYjBtNjBua2oiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1cWhsaGM5bXBnMjA1MWFyN3MxMGozcnVxIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJhd3MuY29nbml0by5zaWduaW4udXNlci5hZG1pbiBvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImF1dGhfdGltZSI6MTcyNjcxMTE2MCwiZXhwIjoxNzI2NzE0NzYwLCJpYXQiOjE3MjY3MTExNjAsImp0aSI6IjNhODVjMjI4LTE3NTgtNGJhMC05YTlhLTEzZWRiMjZiZTMzYyIsInVzZXJuYW1lIjoiaGVpdG9yLmJpdHRlbmNvdXJ0LmF6ZXZlZG9Ac2VsZWN0Z2Vhcm1vdG9ycy5jb20uYnIifQ.EV9UGptDBCevoxCWjXtIr4wEz-fXemK0b_LPlXKLlIf65uqDLlDQB6lD6fzeo619dBFNF2up4303B43vQRkOP1DSpqu-UxRJ6XznSoRAhn2nmmbdBMNdcZgfkk0AXZP3LpHdLk-VwScf1ShIArQ8VfBsiEawg0GgWlJAQRAQfDLpNq7n3XWSpaFurYu49BfAF3f-Lr9xR5CdkLMO15tOXb8ORRU3gWivnv0GaHWa12dDVIfvYr-ofM2GZ0x40tINpqWewRfYI84H1HLv2S3pCnLjpBwmRR6cNiqazaw8r31E0hnioC2-AzWtVc7fzqIjyvk2glCQt0OdZfb8c7GPOg&expires_in=3600&token_type=Bearer";

        // Extrair os parâmetros da URL
        Map<String, String> tokens = extractTokensFromUrl(url);

        if (tokens.containsKey("id_token")) {
            System.out.println("ID Token: " + tokens.get("id_token"));
            decodeAndPrintToken(tokens.get("id_token"));
        }

        if (tokens.containsKey("access_token")) {
            System.out.println("Access Token: " + tokens.get("access_token"));
            decodeAndPrintToken(tokens.get("access_token"));
        }
    }

    // Função para extrair o id_token e access_token da URL
    public static Map<String, String> extractTokensFromUrl(String url) {
        Map<String, String> tokens = new HashMap<>();

        // Extrair a parte da URL após o "?"
        String[] params = url.split("\\#");
        if (params.length > 1) {
            // Quebrar os parâmetros separados por "&"
            String[] queryParams = params[1].split("&");

            // Loop pelos parâmetros para extrair id_token e access_token
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    tokens.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return tokens;
    }

    // Função para decodificar e exibir informações do token JWT
    public static void decodeAndPrintToken(String token) {
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            System.out.println("Token decodificado: " + decodedJwt.getPayload());
        } catch (JWTDecodeException e) {
            System.out.println("Erro ao decodificar o token: " + e.getMessage());
        }
    }
}
