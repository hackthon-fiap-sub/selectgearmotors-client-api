package br.com.selectgearmotors.client.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenDecoder {

    public static void main(String[] args) {
        // Exemplo de URL retornada pelo Cognito
        String urlFragment = "id_token=eyJraWQiOiJ4Y2pqbUVqS2dCcTFydkczRWNXOFAzQ3JkS0R5U0ZLT0huYnBEVjV3eCtnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiTkFLZ2k3Y2twMktHUEpoSFdiVVJQUSIsInN1YiI6IjM0YTgzNDU4LWQwZDEtNzBlZS04YmY0LWJlMjRlMjZhMjkxNCIsImNvZ25pdG86Z3JvdXBzIjpbImFkbWluIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX05iMG02MG5raiIsImN1c3RvbTppZCI6IjcwZDU3ZTFhLTMwYjItNDA3ZC1hNzVjLThmZjc2NDNjODQ2MCIsImNvZ25pdG86dXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIiwiYXVkIjoiNXFobGhjOW1wZzIwNTFhcjdzMTBqM3J1cSIsImV2ZW50X2lkIjoiYzAxNDU3ZDMtOTk4Zi00MTEzLThhZDUtNjgyY2Q4ZjQzNGZmIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3MzMwMTg2NzgsIm5hbWUiOiJIZWl0b3IgQml0dGVuY291cnQgZGUgQXpldmVkbyIsImV4cCI6MTczMzAyMjI3OCwiaWF0IjoxNzMzMDE4Njc4LCJqdGkiOiJlYjBjMWRjMC00MjA5LTRhZWMtYTRiZi1iZDE4YjVkYWM0ZWEiLCJlbWFpbCI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIn0.ECySjRuZ6pgOpz_9D4D_PaJYrPgOnEBmtdswcNEoiIVqr-2pl3bRLDZb9nCHi4HYqDje69n6KabfjrbZc0pSeyi7rtJiLDLKrhd_koRuyfFuO1df148XRDn1ll0A5uIxkeqgJuzzuScLj6vgGn_wifbpTX09ViTFuN-qL47boJmuzNoWhousybhM7OepGVpVpDyFRil9W7ILUaSOMHa1SqVVifRZ3cMMma5gXwSs7vRC5S1gk_PrQMLLqeYJ9hP2xk8MELS97sD3gvExTykFqfhJfrOS3nhtXFpMp9yDNnMwr_9FKio9vdIX0AmPYXsMbE8qLIf0jhwxeCW0_OdbzA&access_token=eyJraWQiOiJJaEtlOE5LQURwbFZDM3NOa3gxa2NwWldFS3FaNExiVktubDRrZm9DdmVnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNGE4MzQ1OC1kMGQxLTcwZWUtOGJmNC1iZTI0ZTI2YTI5MTQiLCJjb2duaXRvOmdyb3VwcyI6WyJhZG1pbiJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9OYjBtNjBua2oiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1cWhsaGM5bXBnMjA1MWFyN3MxMGozcnVxIiwiZXZlbnRfaWQiOiJjMDE0NTdkMy05OThmLTQxMTMtOGFkNS02ODJjZDhmNDM0ZmYiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIG9wZW5pZCBwcm9maWxlIGVtYWlsIiwiYXV0aF90aW1lIjoxNzMzMDE4Njc4LCJleHAiOjE3MzMwMjIyNzgsImlhdCI6MTczMzAxODY3OCwianRpIjoiYjIyM2FmY2UtY2M3YS00ODNiLWIxZmUtNTY5MjRlNmE3ODIxIiwidXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciJ9.NsUhBmFMwn5ruFVLk5VCGJ9js8vZqK5LY8Jqpd0Iwg-kwe_RBmT6Cnvhl2QO7mDG8mph5gPI-PAHwyQ84MIruw0vVaXuuSXfQP0t27aJTaR_mFJaWH3AvF2JjUW8aD60vqKn9OLhMwI3sL_GEspWcV75whJ4HNrLfj74uxBwN-zAJ1IHGdfkEMOUViEknQrUZW-1v6kllru-q2vhHU3J5i66B6-Oo9r3CmAIDlMx-gztl7YobaeOgDCQgeqImCu94xOPslvgGIgDpPbRiOSdhtaFlC0w61DupLX8ZlhSQi94tVpALgIaaTYmQPfr02sGZFk52gO1Rjm7kIIzaKUbZQ&expires_in=3600&token_type=Bearer";

        // Extrair apenas o id_token da URL
        String idToken = extractTokenFromUrlFragment(urlFragment, "id_token");

        // Verifique se o token foi extraído corretamente
        if (idToken != null) {
            try {
                // Decodificar o token JWT
                DecodedJWT decodedJWT = JWT.decode(idToken);

                // Exibe informações do token decodificado
                System.out.println("Subject: " + decodedJWT.getSubject());
                System.out.println("Issuer: " + decodedJWT.getIssuer());
                System.out.println("Expiration: " + decodedJWT.getExpiresAt());
                System.out.println("Email: " + decodedJWT.getClaim("email").asString());
            } catch (Exception e) {
                System.err.println("Erro ao decodificar o token: " + e.getMessage());
            }
        } else {
            System.err.println("id_token não encontrado na URL");
        }
    }

    // Método para extrair o token da URL fragment (id_token ou access_token)
    private static String extractTokenFromUrlFragment(String urlFragment, String tokenName) {
        String[] parts = urlFragment.split("&");
        for (String part : parts) {
            if (part.startsWith(tokenName + "=")) {
                return part.substring((tokenName + "=").length());
            }
        }
        return null;
    }
}
