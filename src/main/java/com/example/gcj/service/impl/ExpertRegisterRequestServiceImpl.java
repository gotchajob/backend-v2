package com.example.gcj.service.impl;

import com.example.gcj.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.expert_register_request.RejectFromRegisterRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.CreateExpertAccountRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertRegisterRequest;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.service.ExpertFormCriteriaService;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.service.EmailService;
import com.example.gcj.util.mapper.ExpertRegisterRequestMapper;
import com.example.gcj.util.status.ExpertRegisterRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpertRegisterRequestServiceImpl implements ExpertRegisterRequestService {
    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
    private final SearchRepository searchRepository;
    private final EmailService emailService;
    private final ExpertService expertService;
    private final UserService userService;
    private final ExpertFormCriteriaService expertFormCriteriaService;


    @Override
    public void create(String email) {
        if (email == null) {
            throw new CustomException("Email is null");
        }
        boolean isExistEmail =  userService.isExistEmail(email);
        if (isExistEmail) {
            throw new CustomException("email is existed!");
        }

        ExpertRegisterRequest _expertRegisterRequest = expertRegisterRequestRepository.getByEmail(email);
        if (_expertRegisterRequest != null) {
            if (_expertRegisterRequest.getStatus() != ExpertRegisterRequestStatus.WAIT_TO_PROCESS) {
                throw new CustomException("you are already request!");
            }
            _expertRegisterRequest.setCreatedAt(new Date());
            expertRegisterRequestRepository.save(_expertRegisterRequest);
            return;
        }

        ExpertRegisterRequest expertRegisterRequest = ExpertRegisterRequest
                .builder()
                .email(email)
                .status(ExpertRegisterRequestStatus.WAIT_TO_PROCESS)
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
        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.SEND_FORM_REGISTER);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        emailService.sendEmailGetLinkFormMentor(expertRegisterRequest.getEmail(), url);
    }

    @Override
    public boolean checkRequest(long id) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("expert register request not found");
        }

        if (!(expertRegisterRequest.getStatus() == ExpertRegisterRequestStatus.SEND_FORM_REGISTER
                || expertRegisterRequest.getStatus() == ExpertRegisterRequestStatus.UPDATING_FORM)) {
            throw new CustomException("invalid url");
        }

        return true;
    }

    @Override
    public boolean banRequest(long id) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("not found expert register request with id = " + id);
        }

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.BAN);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        //todo: ban account
        return true;
    }

    @Override
    public boolean approveFormRegister(long id) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("not found expert register request with id " + id);
        }
        if (expertRegisterRequest.getStatus() != ExpertRegisterRequestStatus.WAIT_TO_APPROVE_FORM) {
            throw new CustomException("invalid expert register request status. current status = " + expertRegisterRequest.getStatus());
        }
        if (expertRegisterRequest.getExpertId() == null) {
            throw new CustomException("expert register request don't expertId");
        }

        long expertId = expertRegisterRequest.getExpertId();
        expertService.verifyExpert(expertId);

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.COMPLETE);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        return true;
    }

    @Override
    public boolean rejectFormRegister(long id, RejectFromRegisterRequestDTO request) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("not found expert register request with id " + id);
        }

        if (expertRegisterRequest.getStatus() != ExpertRegisterRequestStatus.WAIT_TO_APPROVE_FORM) {
            throw new CustomException("invalid expert register request status");
        }

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.UPDATING_FORM);
        expertRegisterRequest.setUrl(request.getUrl());
        expertRegisterRequest.setNote(request.getReasonReject());
        expertRegisterRequestRepository.save(expertRegisterRequest);

        expertFormCriteriaService.create(id, request.getCriteriaList());

        sendEmailRejectExpert(expertRegisterRequest.getEmail(), request.getReasonReject(), request.getUrl());
        return true;
    }

    @Override
    public boolean updateForm(long id, UpdateExpertRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("not found expert register request with id " + id);
        }
        if (expertRegisterRequest.getStatus() != 4) {
            throw new CustomException("expert register request is invalid status. current status is " + expertRegisterRequest.getStatus());
        }
        if (expertRegisterRequest.getExpertId() == null) {
            throw new CustomException("expert id is null!");
        }

        expertService.updateExpert(expertRegisterRequest.getExpertId(), request);

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.WAIT_TO_APPROVE_FORM);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        return true;
    }

    @Override
    public boolean createForm(long id, CreateExpertAccountRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("not found expert register request with id " + id);
        }
        if (expertRegisterRequest.getStatus() != ExpertRegisterRequestStatus.SEND_FORM_REGISTER) {
            throw new CustomException("expert register request is invalid status. current status is " + expertRegisterRequest.getStatus());
        }

        long expertId = userService.createExpertAccount(expertRegisterRequest.getEmail(), request);

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.WAIT_TO_APPROVE_FORM);
        expertRegisterRequest.setExpertId(expertId);
        expertRegisterRequestRepository.save(expertRegisterRequest);
        return true;
    }

    private void sendEmailRejectExpert(String email, String reason, String url) {
        String subject = "Rejection Notification for Expertship Application on Gotchajob";
        String body = "Dear +\n" +
                "\n" +
                "I hope this message finds you well.\n" +
                "\n" +
                "reason: " + reason +
                "\n" +
                "this new url to update form: " + url +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "GotchaJob\n";

        emailService.sendEmail(email, subject, body);
    }
}
