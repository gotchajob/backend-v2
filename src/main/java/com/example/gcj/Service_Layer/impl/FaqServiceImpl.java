package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Faq;
import com.example.gcj.Repository_Layer.repository.FaqRepository;
import com.example.gcj.Service_Layer.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.faq.FaqResponseDTO;
import com.example.gcj.Service_Layer.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.FaqService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.FaqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
    private final static int ACTIVE_FAQ_STATUS = 1;

    private final FaqRepository faqRepository;


    @Override
    public PageResponseDTO<FaqResponseDTO> getList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Faq> faqPage = faqRepository.findByStatus(ACTIVE_FAQ_STATUS, pageable);
        return new PageResponseDTO<>(faqPage.map(FaqMapper::toDto).toList(), faqPage.getTotalPages());
    }

    @Override
    public boolean createFaq(CreateFaqRequestDTO request) {
        Faq faq = Faq
                .builder()
                .answer(request.getAnswer())
                .question(request.getQuestion())
                .status(ACTIVE_FAQ_STATUS)
                .build();

        faqRepository.save(faq);
        return true;
    }

    @Override
    public boolean updateFaq(long id, UpdateFaqRequestDTO request) {
        Faq faq = faqRepository.findById(id);
        if (faq == null) {
            throw new CustomException("Not found faqId= " + id);
        }

        faq.setAnswer(request.getAnswer());
        faq.setQuestion(request.getQuestion());
        faqRepository.save(faq);

        return true;
    }
}
