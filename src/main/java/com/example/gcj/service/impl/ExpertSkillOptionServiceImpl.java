package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.model.SkillOption;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.repository.SkillOptionRepository;
import com.example.gcj.service.ExpertSkillOptionService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.ExpertSkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertSkillOptionServiceImpl implements ExpertSkillOptionService {
    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final ExpertRepository expertRepository;
    private final SkillOptionRepository skillOptionRepository;


    @Override
    public boolean createExpertSkillOption(long expertId, List<CreateExpertSkillOptionDTO> request) {
        boolean isExistExpert = expertRepository.existsById(expertId);
        if (!isExistExpert) {
            throw new CustomException("not found expert with id " + expertId);
        }

        for (CreateExpertSkillOptionDTO r : request) {
            boolean isExistSkillOption = skillOptionRepository.existsById(r.getSkillOptionId());
            if (!isExistSkillOption) {
                continue;
            }

            ExpertSkillOption _expertSkillOption = expertSkillOptionRepository.findByExpertIdAndSkillOptionId(expertId, r.getSkillOptionId());
            if (_expertSkillOption == null) {
                ExpertSkillOption expertSkillOption = ExpertSkillOption
                        .builder()
                        .expertId(expertId)
                        .skillOption(SkillOption.builder().id(r.getSkillOptionId()).build())
                        .defaultPoint(0)
                        .certification(r.getCertificate())
                        .status(1)
                        .build();

                expertSkillOptionRepository.save(expertSkillOption);
                continue;
            }

            if (_expertSkillOption.getStatus() == 0) {
                _expertSkillOption.setDefaultPoint(0);
                _expertSkillOption.setCertification(r.getCertificate());
                _expertSkillOption.setStatus(Status.ACTIVE);
                expertSkillOptionRepository.save(_expertSkillOption);
            }
        }

        return true;
    }

    @Override
    public List<ExpertSkillOptionResponseDTO> getByExpertId(long expertId) {
        List<ExpertSkillOption> expertSkillOptions = expertSkillOptionRepository.findByExpertId(expertId);
        return expertSkillOptions.stream().map(ExpertSkillOptionMapper::toDto).toList();
    }

    @Override
    public boolean updateDefaultPoint(List<UpdateExpertSkillOptionPointRequestDTO> request) {
        for (UpdateExpertSkillOptionPointRequestDTO r : request) {
            ExpertSkillOption expertSkillOption = expertSkillOptionRepository.findById(r.getExpertSkillOptionId());
            if (expertSkillOption == null) {
                continue;
            }
            expertSkillOption.setDefaultPoint(r.getNewDefaultPoint());
            expertSkillOptionRepository.save(expertSkillOption);
        }

        return true;
    }

    @Transactional
    @Override
    public boolean deleteAllByExpertId(long expertId) {
        int rowAffected = expertSkillOptionRepository.updateStatusByExpertId(expertId);
        return rowAffected > 0;
    }
}
