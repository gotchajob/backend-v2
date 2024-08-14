package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.SkillOption;
import com.example.gcj.Repository_Layer.repository.SkillOptionRepository;
import com.example.gcj.Repository_Layer.repository.SkillRepository;
import com.example.gcj.Service_Layer.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.mapper.SkillOptionMapper;
import com.example.gcj.Service_Layer.service.SkillOptionService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillOptionServiceImpl implements SkillOptionService {
    private final SkillOptionRepository skillOptionRepository;
    private final SkillRepository skillRepository;

    @Override
    public List<SkillOptionResponseDTO> getAll(Long categoryId, Long skillId) {
        List<SkillOption> skillOptionList = skillOptionRepository.findByCategoryAndSkillId(categoryId, skillId);
        return skillOptionList.stream().map(SkillOptionMapper::toDto).toList();
    }

    @Override
    public List<SkillOptionResponseDTO> findSkillOptionBySkillId(long skillId) {
        List<SkillOption> skillOptionList = skillOptionRepository.findBySkillId(skillId);
        return skillOptionList.stream().map(SkillOptionMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public boolean createSkillOption(CreateSkillOptionRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (!skillRepository.existsById(request.getSkillId())) {
            throw new CustomException("not found skill with id " + request.getSkillId());
        }

        SkillOption skillOption = SkillOption.builder()
                .name(request.getName())
                .skillId(request.getSkillId())
                .status(1)
                .build();
        skillOptionRepository.save(skillOption);
        return true;
    }

    @Override
    public void deleteSkillOptions(long id) {
        SkillOption skillOption = get(id);

        skillOption.setStatus(0);
        skillOptionRepository.save(skillOption);
    }

    @Override
    public boolean deleteBySkillId(long skillId) {
        List<SkillOption> list = skillOptionRepository.findBySkillId(skillId);
        for (SkillOption skillOption : list) {
            skillOption.setStatus(0);
        }
        skillOptionRepository.saveAll(list);
        return true;
    }

    @Override
    public boolean update(long id, UpdateSkillOptionRequestDTO request) {
        SkillOption skillOption = get(id);

        skillOption.setName(request.getName());
        skillOptionRepository.save(skillOption);

        return true;
    }

    private SkillOption get(long id) {
        SkillOption skillOption = skillOptionRepository.findById(id);
        if (skillOption == null) {
            throw new CustomException("not found skill option with id " + id);
        }

        return skillOption;
    }


}
