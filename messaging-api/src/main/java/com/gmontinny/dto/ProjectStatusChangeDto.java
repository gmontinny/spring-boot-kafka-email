package com.gmontinny.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusChangeDto {
    Long id;

    String productName;

    String authorEmailAddress;

    String authorFullName;
}
