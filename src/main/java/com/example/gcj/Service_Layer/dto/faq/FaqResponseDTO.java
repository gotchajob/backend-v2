package com.example.gcj.Service_Layer.dto.faq;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class FaqResponseDTO implements Serializable {
    private long id;
    private String question;
    private String answer;
}
