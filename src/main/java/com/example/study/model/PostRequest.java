package com.example.study.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @NotBlank(message = "Subject is mandatory")
    private String subject;
    @NotBlank(message = "Text is mandatory")
    private String text;
}
