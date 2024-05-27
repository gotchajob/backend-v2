package com.example.gcj.service.impl;

import com.example.gcj.model.Faq;
import com.example.gcj.repository.FaqRepository;
import com.example.gcj.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
    private final FaqRepository faqRepository;

    @Override
    public void getList() {
        faqRepository.findAll();
        faqRepository.save(new Faq());
    }
}
