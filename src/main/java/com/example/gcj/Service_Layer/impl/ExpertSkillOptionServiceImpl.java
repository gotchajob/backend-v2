package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ExpertSkillOption;
import com.example.gcj.Repository_Layer.model.SkillOption;
import com.example.gcj.Repository_Layer.repository.ExpertRepository;
import com.example.gcj.Repository_Layer.repository.ExpertSkillOptionRepository;
import com.example.gcj.Repository_Layer.repository.ExpertSkillRatingRepository;
import com.example.gcj.Repository_Layer.repository.SkillOptionRepository;
import com.example.gcj.Service_Layer.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertSkillOptionService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.ExpertSkillOptionMapper;
import com.example.gcj.Shared.util.status.ExpertSkillOptionStatus;
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
    private final ExpertSkillRatingRepository expertSkillRatingRepository;


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
        List<ExpertSkillOptionResponseDTO> list = expertSkillOptions.stream().map(ExpertSkillOptionMapper::toDto).toList();
        addRating(list);
        return list;
    }

    @Override
    public List<ExpertSkillOptionResponseDTO> getByExpertId(long expertId, int status) {
        List<ExpertSkillOption> expertSkillOptions = expertSkillOptionRepository.findByExpertIdAndStatus(expertId, status);
        List<ExpertSkillOptionResponseDTO> list = expertSkillOptions.stream().map(ExpertSkillOptionMapper::toDto).toList();
        addRating(list);
        return list;
    }

    private void addRating(List<ExpertSkillOptionResponseDTO> list) {
        for (ExpertSkillOptionResponseDTO item : list) {
            long totalRating = expertSkillRatingRepository.countByExpertSkillOptionId(item.getId());
            long sum = expertSkillRatingRepository.sumRating(item.getId());

            item.setTotalRating(totalRating);
            item.setSumPoint(sum);
        }
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

    @Override
    public void get(List<Long> skillOptionId) {

    }

    @Override
    public boolean show(long id, long expertId) {
        ExpertSkillOption expertSkillOption = get(id);

        if (expertSkillOption.getExpertId() != expertId) {
            throw new CustomException("expert skill option not yours");
        }

        if (expertSkillOption.getStatus() == ExpertSkillOptionStatus.SHOW) {
            return true;
        }

        expertSkillOption.setStatus(ExpertSkillOptionStatus.SHOW);
        save(expertSkillOption);

        return true;
    }

    @Override
    public boolean hidden(long id, long expertId) {
        ExpertSkillOption expertSkillOption = get(id);

        if (expertSkillOption.getExpertId() != expertId) {
            throw new CustomException("expert skill option not yours");
        }

        if (expertSkillOption.getStatus() == ExpertSkillOptionStatus.HIDDEN) {
            return true;
        }

        expertSkillOption.setStatus(ExpertSkillOptionStatus.HIDDEN);
        save(expertSkillOption);

        return true;
    }

    private ExpertSkillOption save(ExpertSkillOption expertSkillOption) {
        if (expertSkillOption == null) {
            return null;
        }

        return expertSkillOptionRepository.save(expertSkillOption);
    }

    private ExpertSkillOption get(long id) {
        ExpertSkillOption expertSkillOption = expertSkillOptionRepository.findById(id);
        if (expertSkillOption == null) {
            throw new CustomException("not found expert skill option");
        }

        return expertSkillOption;
    }
}
