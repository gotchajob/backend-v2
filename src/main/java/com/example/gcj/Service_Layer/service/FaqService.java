package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.faq.FaqResponseDTO;
import com.example.gcj.Service_Layer.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface FaqService {

    PageResponseDTO<FaqResponseDTO> getList(int pageNumber, int pageSize);
    boolean createFaq(CreateFaqRequestDTO request);
    boolean updateFaq(long id, UpdateFaqRequestDTO request);
}
