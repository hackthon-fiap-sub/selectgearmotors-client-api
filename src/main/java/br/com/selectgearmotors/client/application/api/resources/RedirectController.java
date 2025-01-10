package br.com.selectgearmotors.client.application.api.resources;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/api")
    public ResponseEntity<String> home(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("{\"message\": \"Bem-vindo à API! Acesse a documentação em: " + baseUrl + "/swagger-ui/index.html\"}");
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/api/swagger-ui.html"; // Redireciona para o Swagger UI
    }
}
