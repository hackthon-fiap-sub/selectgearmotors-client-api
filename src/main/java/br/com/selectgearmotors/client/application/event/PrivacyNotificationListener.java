package br.com.selectgearmotors.client.application.event;

import br.com.selectgearmotors.client.application.client.RequestService;
import br.com.selectgearmotors.client.application.event.dto.MessageNotification;
import br.com.selectgearmotors.client.application.event.dto.SNSNotification;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.ports.out.ClientRepositoryPort;
import br.com.selectgearmotors.client.core.utils.JsonUtil;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PrivacyNotificationListener {

    @Value("${aws.privacyNotification.queueName}")
    private String queueName;

    private final SqsTemplate sqsTemplate;
    private final ClientRepositoryPort clientRepositoryPort;
    private final RequestService requestService;

    @Autowired
    public PrivacyNotificationListener(SqsTemplate sqsTemplate, ClientRepositoryPort clientRepositoryPort, RequestService requestService) {
        this.sqsTemplate = sqsTemplate;
        this.clientRepositoryPort = clientRepositoryPort;
        this.requestService = requestService;
    }

    //@SqsListener("${aws.privacyNotification.queueName}")
    public void listen(Message<?> message, Acknowledgement acknowledgement) {
        try {
            log.info("Message received from queue: {}", message);
            log.info("Message Payload: {}", message.getPayload());
            if (message != null && message.getPayload() != null) {
                String messagePayload = message.getPayload().toString();
                log.info("Message Payload: {}", messagePayload);

                SNSNotification snsNotification = JsonUtil.fromJson(messagePayload, SNSNotification.class);
                String snsMessage = snsNotification.getMessage();

                MessageNotification messageNotification = JsonUtil.jsonToObject(snsMessage, MessageNotification.class);
                if (messageNotification != null) {
                    log.info("Message Notification: {}", messageNotification);
                    String userName = messageNotification.requestId();
                    String status = messageNotification.status();
                    Client clientFound = clientRepositoryPort.findByCode(userName);
                    if (clientFound != null && !status.equals("REMOVED")) {
                        log.info("Client Found: {}", clientFound);
                        clientRepositoryPort.remove(clientFound.getId());
                        requestService.updateRequestStatus(userName, "REMOVED");
                    } else {
                        requestService.updateRequestStatus(userName, "REJECTED");
                        log.info("Client not excluded: {}", userName);
                    }
                    acknowledgement.acknowledge();
                }
            }
        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }
}