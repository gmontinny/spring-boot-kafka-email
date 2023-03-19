package com.gmontinny.listener;

import com.gmontinny.dto.ProjectStatusChangeDto;
import com.gmontinny.service.EmailService;
import com.gmontinny.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectStatusChangeMailerListener {
    private final EmailService emailService;
    private final TemplateService templateService;

    @KafkaListener(topicPattern = "${kafka.topics.project-status-changed}", autoStartup = "${kafka.enabled}")
    public void listenToProjectStatusChange(ConsumerRecord<String, ProjectStatusChangeDto> record) {
        log.info("Solicitação de alteração de status do projeto recebida: " + record.toString());

        ProjectStatusChangeDto payload = record.value();

        if (payload.getAuthorEmailAddress() == null) {
            log.warn("Ignorando solicitação de envio de e-mail sem endereço de e-mail: " + record.toString());
            return;
        }

        try {
            emailService.sendEmail(
                payload.getAuthorEmailAddress(),
                "Seu pedido",
                templateService.generateProjectStatusChangeEmail(payload)
            );
        } catch (MailException e) {
            log.error("Não foi possível enviar email", e);
        }
    }
}
