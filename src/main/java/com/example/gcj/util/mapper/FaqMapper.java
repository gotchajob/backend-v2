package com.example.gcj.util.mapper;

import com.example.gcj.dto.faq.FaqResponseDTO;
import com.example.gcj.model.Faq;

public class FaqMapper {
    public static FaqResponseDTO toDto(Faq faq) {
        return FaqResponseDTO
                .builder()
                .id(faq.getId())
                .answer(faq.getAnswer())
                .question(faq.getQuestion())
                .build();
    }
}
