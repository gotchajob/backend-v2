package com.example.gcj.service.impl;

import com.example.gcj.dto.mentor_register_request.GetMentorRegisterRequestResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.MentorRegisterRequest;
import com.example.gcj.repository.MentorRegisterRequestRepository;
import com.example.gcj.service.MentorRegisterRequestService;
import com.example.gcj.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorRegisterRequestServiceImpl implements MentorRegisterRequestService {
    private final static int ACTIVE_STATUS = 1;
    private final static int APPROVE_STATUS = 2;
    private final static int REJECT_STATUS = 3;

    private final MentorRegisterRequestRepository mentorRegisterRequestRepository;
    private final EmailService emailService;

    @Override
    public void create(String email) {
        if (email == null) {
            throw new CustomException("Email is null");
        }

        MentorRegisterRequest mentorRegisterRequest = MentorRegisterRequest
                .builder()
                .email(email)
                .status(ACTIVE_STATUS)
                .build();
        mentorRegisterRequestRepository.save(mentorRegisterRequest);
    }

    @Override
    public GetMentorRegisterRequestResponseDTO get(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<MentorRegisterRequest> list = mentorRegisterRequestRepository.getAllByStatus(ACTIVE_STATUS, pageable);
        long total = mentorRegisterRequestRepository.countByStatus(ACTIVE_STATUS);

        return new GetMentorRegisterRequestResponseDTO(list, total);
    }

    @Override
    public void approveRegister(long id, String url) {
        MentorRegisterRequest mentorRegisterRequest = mentorRegisterRequestRepository.getById(id);
        if (mentorRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }

        mentorRegisterRequest.setUrl(url);
        mentorRegisterRequest.setStatus(APPROVE_STATUS);
        mentorRegisterRequestRepository.save(mentorRegisterRequest);

        emailService.sendEmailGetLinkFormMentor(mentorRegisterRequest.getEmail(), url);
    }

    @Override
    public void rejectRegister(long id, String note) {
        MentorRegisterRequest mentorRegisterRequest = mentorRegisterRequestRepository.getById(id);
        if (mentorRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }

        mentorRegisterRequest.setStatus(REJECT_STATUS);
        mentorRegisterRequest.setNote(note);
        mentorRegisterRequestRepository.save(mentorRegisterRequest);
    }
}
