package com.example.gcj.service;

import com.example.gcj.dto.expert_request_reject.ExpertRegisterUpdateRequestDTO;
import com.example.gcj.model.ExpertRegisterUpdate;

public interface ExpertRegisterUpdateService {
    ExpertRegisterUpdate updateUpdateExpertRequest(ExpertRegisterUpdateRequestDTO request);
}
