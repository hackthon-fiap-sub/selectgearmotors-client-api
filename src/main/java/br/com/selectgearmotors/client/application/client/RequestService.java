package br.com.selectgearmotors.client.application.client;

import br.com.selectgearmotors.client.application.client.dto.UpdateStatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RequestService {

    private final RestTemplate restTemplate;

    @Value("${api.gateway.url:https://umtpix5m64.execute-api.us-east-1.amazonaws.com/Prod}")
    private String apiGatewayUrl;

    public RequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> updateRequestStatus(String requestId, String newStatus) {
        String url = String.format("%s/delete-request/%s/status", apiGatewayUrl, requestId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        UpdateStatusRequest request = new UpdateStatusRequest(newStatus);
        HttpEntity<UpdateStatusRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }
}
