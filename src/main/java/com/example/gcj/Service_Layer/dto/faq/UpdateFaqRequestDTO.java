package com.example.gcj.Service_Layer.dto.faq;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UpdateFaqRequestDTO implements Serializable {
    private String question;
    private String answer;
}
