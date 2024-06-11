package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_request_reject.ExpertRegisterUpdateRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertRegisterRequest;
import com.example.gcj.model.ExpertRegisterUpdate;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.repository.ExpertRegisterUpdateRepository;
import com.example.gcj.service.ExpertRegisterUpdateService;
import com.example.gcj.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertRegisterUpdateServiceImpl implements ExpertRegisterUpdateService {
    private final ExpertRegisterUpdateRepository expertRegisterUpdateRepository;
    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
    private final EmailService emailService;

    @Override
    public ExpertRegisterUpdate updateUpdateExpertRequest(ExpertRegisterUpdateRequestDTO request) {
        ExpertRegisterRequest expertRequest = expertRegisterRequestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new CustomException("Expert request not found"));

        Integer status = request.getStatus();

        if (status == null || (status != 1 && status != 2)) {
            throw new CustomException("Invalid status. Status must be 1 or 2.");
        }

        ExpertRegisterUpdate updateRequest = expertRegisterUpdateRepository.findByRequestId(request.getRequestId());

        if (updateRequest == null) {
            updateRequest = ExpertRegisterUpdate.builder()
                    .requestId(request.getRequestId())
                    .note(request.getNote())
                    .updateUrl(request.getUpdateUrl())
                    .status(status)
                    .count(status == 2 ? 1 : null)
                    .build();
        } else {
            updateRequest.setStatus(status);
            if (updateRequest.getStatus() == 2) {
                if (updateRequest.getCount() == null) {
                    updateRequest.setStatus(1);
                } else {
                    updateRequest.setStatus(updateRequest.getCount() + 1);
                }
            }
            if (request.getNote() != null) {
                updateRequest.setNote(request.getNote());
            }
            if (request.getUpdateUrl() != null) {
                updateRequest.setUpdateUrl(request.getUpdateUrl());
            }
        }

        updateRequest = expertRegisterUpdateRepository.save(updateRequest);

        String email = expertRequest.getEmail();
        String note = updateRequest.getNote();
        String updateUrl = updateRequest.getUpdateUrl();
        int statusUpdated = updateRequest.getStatus();

        emailService.updateExpertRegisForm(email, note, updateUrl, statusUpdated);
        return updateRequest;
    }
}
