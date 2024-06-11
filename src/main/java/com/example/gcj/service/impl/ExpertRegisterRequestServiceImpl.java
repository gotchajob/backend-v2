package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertRegisterRequest;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.util.EmailService;
import com.example.gcj.util.mapper.ExpertRegisterRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertRegisterRequestServiceImpl implements ExpertRegisterRequestService {
    private final static int ACTIVE_STATUS = 1;
    private final static int APPROVE_STATUS = 2;
    private final static int REJECT_STATUS = 3;
    private final static int UPDATING_STATUS = 4;

    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
    private final SearchRepository searchRepository;
    private final EmailService emailService;

    @Override
    public void create(String email) {
        if (email == null) {
            throw new CustomException("Email is null");
        }

        //check email is ban

        ExpertRegisterRequest _expertRegisterRequest = expertRegisterRequestRepository.getByEmailAndStatus(email, ACTIVE_STATUS);
        if (_expertRegisterRequest != null) {

            _expertRegisterRequest.setCreatedAt(new Date());
            expertRegisterRequestRepository.save(_expertRegisterRequest);
            return;
        }


        ExpertRegisterRequest expertRegisterRequest = ExpertRegisterRequest
                .builder()
                .email(email)
                .status(ACTIVE_STATUS)
                .build();
        expertRegisterRequestRepository.save(expertRegisterRequest);
    }

    @Override
    public PageResponseDTO<ExpertRegisterRequestResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<ExpertRegisterRequest> expertRegisterRequestPage = searchRepository.getEntitiesPage(ExpertRegisterRequest.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(expertRegisterRequestPage.map(ExpertRegisterRequestMapper::toDto).toList(), expertRegisterRequestPage.getTotalPages());
    }

    @Override
    public ExpertRegisterRequestResponseDTO get(long id) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("expert register request not found with id=" + id);
        }

        return ExpertRegisterRequestMapper.toDto(expertRegisterRequest);
    }

    @Override
    public void approveRegister(long id, String url) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }

        expertRegisterRequest.setUrl(url);
        expertRegisterRequest.setStatus(APPROVE_STATUS);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        emailService.sendEmailGetLinkFormMentor(expertRegisterRequest.getEmail(), url);
    }

    @Override
    public void rejectRegister(long id, String note) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }
        expertRegisterRequest.setStatus(REJECT_STATUS);
        expertRegisterRequest.setNote(note);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        emailService.updateExpertRegisForm(expertRegisterRequest.getEmail(), note, REJECT_STATUS);
    }

    @Override
    public boolean checkRequest(long id) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("expert register request not found");
        }

        if (!(expertRegisterRequest.getStatus() == 2 || expertRegisterRequest.getStatus() == 4)) {
            throw new CustomException("invalid url");
        }

        return true;
    }
}
