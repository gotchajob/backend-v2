package com.example.gcj.dto.faq;

import jakarta.persistence.GeneratedValue;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UpdateFaqRequestDTO implements Serializable {
    private String question;
    private String answer;
}
