package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_register_request.GetExpertRegisterRequestResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertRegisterRequest;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.util.EmailService;
import lombok.RequiredArgsConstructor;
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

    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
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
    public GetExpertRegisterRequestResponseDTO get(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<ExpertRegisterRequest> list = expertRegisterRequestRepository.getAllByStatus(ACTIVE_STATUS, pageable);
        long total = expertRegisterRequestRepository.countByStatus(ACTIVE_STATUS);

        return new GetExpertRegisterRequestResponseDTO(list, total);
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
    }
}
