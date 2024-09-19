package br.com.selectgearmotors.client.commons.util;

public class JwtDecoderExample {

    public static void main(String[] args) {
        // Sua string original com os tokens concatenados
        String tokenString = "eyJraWQiOiJ4Y2pqbUVqS2dCcTFydkczRWNXOFAzQ3JkS0R5U0ZLT0huYnBEVjV3eCtnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiaGN4ZHgwamx6WndlV1htcGJ2a0dSZyIsInN1YiI6IjM0YTgzNDU4LWQwZDEtNzBlZS04YmY0LWJlMjRlMjZhMjkxNCIsImNvZ25pdG86Z3JvdXBzIjpbImFkbWluIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX05iMG02MG5raiIsImN1c3RvbTppZCI6IjcwZDU3ZTFhLTMwYjItNDA3ZC1hNzVjLThmZjc2NDNjODQ2MCIsImNvZ25pdG86dXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIiwiYXVkIjoiNXFobGhjOW1wZzIwNTFhcjdzMTBqM3J1cSIsImV2ZW50X2lkIjoiZmUwNzcxZGQtOWIwNC00MmRiLWE5MGUtZjc3ODU3NWRmMTcwIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3MjY3MDYxOTcsIm5hbWUiOiJIZWl0b3IgQml0dGVuY291cnQgZGUgQXpldmVkbyIsImV4cCI6MTcyNjcwOTc5NywiaWF0IjoxNzI2NzA2MTk3LCJqdGkiOiIyMTQwM2Y3YS1hYjQ4LTQwM2YtYWY3MS1kMmNiZDA2OWVkNzIiLCJlbWFpbCI6ImhlaXRvci5iaXR0ZW5jb3VydC5hemV2ZWRvQHNlbGVjdGdlYXJtb3RvcnMuY29tLmJyIn0.EsJmpyacNoXscsDgdL0D-1-1Dw_5kXzZzVWBJp9fG8dOOE3GcCVjX-04byz0MfXeqFQfy-DNud19OFRU0Uu7zXCnk9U7huGZUcmhLg1RGTZwTYZJLoxCrlJVas0Qktyr5PmKJ8GBkZdMKEieHDZ-kIS97WHFRbgahDAHt-7eeYleVAwOBwtE3HU0clyRx8w11Eg4xa8ScR-q9sYa_p2ixDi7M2jU74IkFxNN57nf1iqsVIHlsqVHHLxdvP0aCxAO3aFMYSf7d2OnhX0lfUKTwS_nrX1vYwotRSviA5bi370hO5ucFtCiQK2TWTnkxRNMgTuXI1E42xSjTD_34gLH_w&access_token=eyJraWQiOiJJaEtlOE5LQURwbFZDM3NOa3gxa2NwWldFS3FaNExiVktubDRrZm9DdmVnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNGE4MzQ1OC1kMGQxLTcwZWUtOGJmNC1iZTI0ZTI2YTI5MTQiLCJjb2duaXRvOmdyb3VwcyI6WyJhZG1pbiJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9OYjBtNjBua2oiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1cWhsaGM5bXBnMjA1MWFyN3MxMGozcnVxIiwiZXZlbnRfaWQiOiJmZTA3NzFkZC05YjA0LTQyZGItYTkwZS1mNzc4NTc1ZGYxNzAiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIG9wZW5pZCBwcm9maWxlIGVtYWlsIiwiYXV0aF90aW1lIjoxNzI2NzA2MTk3LCJleHAiOjE3MjY3MDk3OTcsImlhdCI6MTcyNjcwNjE5NywianRpIjoiNTY4ZTBlZDItYWQzYy00ZThiLTgxMTAtMGM2YjU4MzYxMjc0IiwidXNlcm5hbWUiOiJoZWl0b3IuYml0dGVuY291cnQuYXpldmVkb0BzZWxlY3RnZWFybW90b3JzLmNvbS5iciJ9.uZJnc5BcmLCt_OktWI1_CLTBcPHpfgjAoGbOoldmE2XiBusyMp9QeJRze-NdS5Q_fW7sHpO7ec4BPDcDtdLShqSwxG7chN5FSfvQ-PJYA03Zrq385zAkVRqVyuuSYcpIpptod32HzcY78t8gEIn1cgA9_4VlE2IDJbMM902UNwYIQIKdaTMCHIeTUksUowr3LLm-uuPy5KrlhfogNi9_kJd5qWde0DTY4sRFzFI0PKHtZsIqwjOkMmlthq37kdqeQQfi36e8_-b9Dv6oxHeIXtytwVPs60M6a6USzEqcDWbak5fJyiHCDh43cZ4wrns6Qk3M54Kvg6WNOAlUeHLDUQ";

        // Separar o ID Token e o Access Token
        String[] parts = tokenString.split("&access_token=");

        // O ID Token está na primeira parte
        String idToken = parts[0];

        // O Access Token está na segunda parte, se necessário
        String accessToken = parts[1];

        // Decodificar o ID Token
        com.auth0.jwt.interfaces.DecodedJWT decodedJwt = com.auth0.jwt.JWT.decode(idToken);

        // Exibir o payload do token
        System.out.println("ID Token Payload: " + decodedJwt.getPayload());

        // Se precisar do Access Token, você pode fazer o mesmo com ele
        com.auth0.jwt.interfaces.DecodedJWT decodedAccessToken = com.auth0.jwt.JWT.decode(accessToken);
        System.out.println("Access Token Payload: " + decodedAccessToken.getPayload());
    }
}
