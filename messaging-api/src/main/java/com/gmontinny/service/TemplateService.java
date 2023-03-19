package com.gmontinny.service;

import com.gmontinny.dto.EmailContentDto;
import com.gmontinny.dto.ProjectStatusChangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final SpringTemplateEngine templateEngine;

    public EmailContentDto generateProjectStatusChangeEmail(ProjectStatusChangeDto projectStatus) {
        Context context = new Context();
        context.setVariable("fullName", projectStatus.getAuthorFullName());
        context.setVariable("productName", projectStatus.getProductName());

        return EmailContentDto
            .builder()
            .text(templateEngine.process("project-status-change.txt", context))
            .html(templateEngine.process("project-status-change.html", context))
            .build();
    }
}
