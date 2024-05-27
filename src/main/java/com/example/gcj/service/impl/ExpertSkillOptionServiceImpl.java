package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.service.ExpertSkillOptionService;
import com.example.gcj.util.mapper.ExpertSkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertSkillOptionServiceImpl implements ExpertSkillOptionService {
    private final ExpertSkillOptionRepository expertSkillOptionRepository;

    @Override
    public boolean createExpertSkillOption(long expertId, List<CreateExpertSkillOptionDTO> request) {
        for (CreateExpertSkillOptionDTO r : request) {
            ExpertSkillOption expertSkillOption = ExpertSkillOption
                    .builder()
                    .expertId(expertId)
                    .skillOptionId(r.getSkillOptionId())
                    .defaultPoint(0)
                    .certification(r.getCertificate())
                    .status(1)
                    .build();

            //todo: refactor to save list to db
            expertSkillOptionRepository.save(expertSkillOption);
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
}
