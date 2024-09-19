package br.com.selectgearmotors.client.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenDecoder {

    public static void main(String[] args) {
        // Exemplo de URL retornada pelo Cognito
        String urlFragment = "id_token=eyJraWQiOiJ4Y2pqbUVqS2dCcTFydkczRWNXOFAzQ3JkS0R5U0ZLT0huYnBEVjV3eCtnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiNXh5czdrdnVNekJHLXNrbjIwOTRYdyIsInN1YiI6IjM0YTgzNDU4LWQwZDEtNzBlZS04YmY0LWJlMjRlMjZhMjkxNCIsImNvZ25pdG86Z3JvdXBzIjpbImFkbWluIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX05iMG02MG5raiIsImN1c3RvbTppZCI6IjcwZDU3ZTFhLTMwYjItNDA3ZC1hNzVjLThmZjc2NDNjODQ2MCIsImNvZ25pdG86dXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIiwiYXVkIjoiNXFobGhjOW1wZzIwNTFhcjdzMTBqM3J1cSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNzI2NzA3MDc3LCJuYW1lIjoiSGVpdG9yIEJpdHRlbmNvdXJ0IGRlIEF6ZXZlZG8iLCJleHAiOjE3MjY3MTA2NzcsImlhdCI6MTcyNjcwNzA3NywianRpIjoiZjYwMDcwYzctNmQ2ZS00ZTZhLWEwOTItNWQyNTUwMzYwMzkzIiwiZW1haWwiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciJ9.RWHlvRIb2OvpxYbjYAy8H_7eBgEB1RoBu7d-W-bHaamYODp3NeQQ6e7Xix7QHXcjW4GA7lxnCdEvPRxBmw2B4u0vwit517IVmgW4rMbdYoOdBCUCTWbaPwOYp9XExS0oC8nXuinTdhldIx52tMD1JOjqdktSUadfphRT4Rte0CxzdWFEPE9wZLjzj3h6cuHh91_du7ZpyqUzYqJWo4hyrH_RK6LwM72Dh3XOEGzMQNNwyVRle-_ukAhFweCWRfvg_gJ4vcsHRTWY8DsO3BJwr_V5EbG6EiWXE-yFtduczwTgYNVJ5AOKlMHmcgGqHo270ewLBRVmhc85M90pFrnvPQ&access_token=eyJraWQiOiJJaEtlOE5LQURwbFZDM3NOa3gxa2NwWldFS3FaNExiVktubDRrZm9DdmVnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNGE4MzQ1OC1kMGQxLTcwZWUtOGJmNC1iZTI0ZTI2YTI5MTQiLCJjb2duaXRvOmdyb3VwcyI6WyJhZG1pbiJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9OYjBtNjBua2oiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1cWhsaGM5bXBnMjA1MWFyN3MxMGozcnVxIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJhd3MuY29nbml0by5zaWduaW4udXNlci5hZG1pbiBvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImF1dGhfdGltZSI6MTcyNjcwNzA3NywiZXhwIjoxNzI2NzEwNjc3LCJpYXQiOjE3MjY3MDcwNzcsImp0aSI6ImQ2ODgwMjRiLTEyNGUtNDc3Ni1iYmEyLWE2NDk5YWVmMWY5OSIsInVzZXJuYW1lIjoiaGVpdG9yLmJpdHRlbmNvdXJ0LmF6ZXZlZG9Ac2VsZWN0Z2Vhcm1vdG9ycy5jb20uYnIifQ.WAAuO9ypWehmh5yB9GHbK4_TztdTbPrvbP-TdJojUqWpcEYBTfo1OUdewVolVSmPwd3up2-jfjYntI2bgRF5OsH4uHc5rcT_FCmzaqN8um8Irodlj3iVhGiKsoZyxTLQmLazn-eLHm_WF2nefXGz9jP-hrxlDoioWIaehwzJWJXsikUtOMgV4hojbkn5G_ItQAApJVwoYSF_K2g-LPv3XJKEo_7PPIsgCQafpzRxlMXlwQFBlaAWA4anXaR3q6lA9t3jgp3NK8QFJzTi4pYHWsvIunZRHsDfW4MM5MiTiA56aQ1GY6GoIlSrk6_7zTM0fYqf6LqQIyx9Kbfprn_gcw&expires_in=3600&token_type=Bearer";

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
