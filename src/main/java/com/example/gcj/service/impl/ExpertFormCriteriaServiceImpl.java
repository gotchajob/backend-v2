package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_form_criteria.CreateExpertFormCriteriaRequestDTO;
import com.example.gcj.dto.expert_form_criteria.ExpertFormCriteriaResponseDTO;
import com.example.gcj.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertFormCriteria;
import com.example.gcj.repository.ExpertFormCriteriaRepository;
import com.example.gcj.repository.ExpertRegisterRequestRepository;
import com.example.gcj.service.ExpertFormCriteriaService;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.util.mapper.ExpertFormCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertFormCriteriaServiceImpl implements ExpertFormCriteriaService {
    private final ExpertFormCriteriaRepository expertFormCriteriaRepository;
    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;

    @Override
    public boolean create(long expertRegisterRequestId, List<CreateExpertFormCriteriaRequestDTO> request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        if (!expertRegisterRequestRepository.existsById(expertRegisterRequestId)) {
            throw new CustomException("not found expert register request with id " + expertRegisterRequestId);
        }

        delete(expertRegisterRequestId); // reset

        for (CreateExpertFormCriteriaRequestDTO r : request) {
            ExpertFormCriteria expertFormCriteria = ExpertFormCriteria
                    .builder()
                    .expertRequestId(expertRegisterRequestId)
                    .criteria(r.getCriteria())
                    .description(r.getDescription())
                    .status(r.getStatus())
                    .build();
            expertFormCriteriaRepository.save(expertFormCriteria);
        }

        return true;
    }

    @Override
    public List<ExpertFormCriteriaResponseDTO> getList(Long expertRequestId) {
        List<ExpertFormCriteria> expertFormCriteriaList = expertRequestId == null?
                expertFormCriteriaRepository.findAll() : expertFormCriteriaRepository.findByExpertRequestId(expertRequestId);

        return expertFormCriteriaList.stream().map(ExpertFormCriteriaMapper::toDto).toList();
    }

    @Transactional
    public boolean delete(long expertRequestId) {
        int rowAffect = expertFormCriteriaRepository.updateByExpertRequestId(expertRequestId);

        return rowAffect > 0;
    }
}
