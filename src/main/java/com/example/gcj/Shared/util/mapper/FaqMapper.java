package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.faq.FaqResponseDTO;
import com.example.gcj.Repository_Layer.model.Faq;

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
