package com.example.gcj.Service_Layer.dto.faq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CreateFaqRequestDTO implements Serializable {
    @NotNull(message = "question must not null")
    private String question;
    @NotBlank(message = "answer must not blank")
    private String answer;
}
