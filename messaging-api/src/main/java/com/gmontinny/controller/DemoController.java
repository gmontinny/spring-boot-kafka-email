package com.gmontinny.controller;

import com.gmontinny.config.KafkaProperties;
import com.gmontinny.dto.ProjectStatusChangeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class DemoController {
    private KafkaTemplate<String, ProjectStatusChangeDto> kakfaProducer;
    private KafkaProperties kafkaProperties;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendProjectStatusEmail(@RequestBody ProjectStatusChangeDto statusChange) {
        log.info("Sending mailing request: " + statusChange.toString());
        kakfaProducer.send(kafkaProperties.getTopics().getProjectStatusChanged(), statusChange);
    }
}
