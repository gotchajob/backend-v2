package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.expert_register_request.RejectFromRegisterRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertRegisterRequest;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.EmailService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.ExpertRegisterRequestMapper;
import com.example.gcj.util.status.ExpertRegisterRequestStatus;
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
    private final static int REJECT_STATUS = 3;

    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
    private final SearchRepository searchRepository;
    private final EmailService emailService;
    private final ExpertService expertService;
    private final UserService userService;

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
    public void rejectRegister(long id, String note) {
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(id);
        if (expertRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.UPDATING_FORM);
        expertRegisterRequest.setNote(note);
        expertRegisterRequestRepository.save(expertRegisterRequest);

        //todo: send mail to update
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

        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.UPDATING_FORM);
        expertRegisterRequest.setUrl(request.getUrl());
        expertRegisterRequest.setNote(request.getReasonReject());
        expertRegisterRequestRepository.save(expertRegisterRequest);

        sendEmailRejectExpert(expertRegisterRequest.getEmail(), request.getReasonReject(), request.getUrl());
        return true;
    }

    private void sendEmailRejectExpert(String email, String reason, String url) {
        String subject = "Rejection Notification for Expertship Application on Gotchajob";
        String body = "Dear +\n" +
                "\n" +
                "I hope this message finds you well.\n" +
                "\n" +
                "reason: " + reason +
                "this new url to update form: " + url +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "GotchaJob\n";

        emailService.sendEmail(email, subject, body);
    }
}
