package com.example.gcj.service;

import com.example.gcj.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.dto.faq.FaqResponseDTO;
import com.example.gcj.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;

public interface FaqService {

    PageResponseDTO<FaqResponseDTO> getList(int pageNumber, int pageSize);
    boolean createFaq(CreateFaqRequestDTO request);
    boolean updateFaq(long id, UpdateFaqRequestDTO request);
}
