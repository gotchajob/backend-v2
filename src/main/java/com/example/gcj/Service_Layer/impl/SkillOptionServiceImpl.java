package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Skill;
import com.example.gcj.Repository_Layer.model.SkillOption;
import com.example.gcj.Repository_Layer.repository.SkillOptionRepository;
import com.example.gcj.Repository_Layer.repository.SkillRepository;
import com.example.gcj.Service_Layer.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.service.SkillOptionService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.SkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillOptionServiceImpl implements SkillOptionService {
    private final SkillOptionRepository skillOptionRepository;
    private final SkillRepository skillRepository;

    @Override
    public List<SkillOptionResponseDTO> getAll() {
        List<SkillOption> skillOptionList = skillOptionRepository.findAll();
        return skillOptionList.stream().map(SkillOptionMapper::toDto).toList();
    }

    @Override
    public List<SkillOptionResponseDTO> findSkillOptionBySkillId(long skillId) {
        List<SkillOption> skillOptionList = skillOptionRepository.findBySkillId(skillId);
        return skillOptionList.stream().map(SkillOptionMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public boolean createSkillOption(CreateSkillOptionRequestDTO request) {
        SkillOption skillOption = SkillOption.builder()
                .name(request.getName())
                .skillId(request.getSkillId())
                .build();
        skillOptionRepository.save(skillOption);
        return true;
    }

    @Override
    public List<UpdateSkillOptionRequestDTO> updateSkillOptions(List<UpdateSkillOptionRequestDTO> request) {
        List<UpdateSkillOptionRequestDTO> updateSkillOptionList = new ArrayList<>();
        for (UpdateSkillOptionRequestDTO dto : request) {
            Optional<SkillOption> existingSkillOption = skillOptionRepository.findById(dto.getId());
            if(existingSkillOption.isPresent()) {
                SkillOption existing = existingSkillOption.get();
                existing.setName(dto.getName());
                existing.setSkillId(dto.getSkillId());
                skillOptionRepository.save(existing);
                updateSkillOptionList.add(convertToDTO(existing));
            }
        }
        return updateSkillOptionList;
    }


    @Override
    public void deleteSkillOptions(long id) {
        if(!skillOptionRepository.existsById(id)) {
            throw new CustomException("Skill Option is not found with id " + id);
        }
        skillOptionRepository.deleteById(id);

    }

    @Override
    public List<SkillOptionResponseDTO> findSkillOptionByCategory(long categoryId) {
        List<SkillOption> skillOptionList = new ArrayList<>();
        List<Skill> skills =  skillRepository.findAllByCategoryId(categoryId);
        for (Skill skill : skills) {
            skillOptionList.addAll(skillOptionRepository.findBySkillId(skill.getId()));
        }

        return skillOptionList.stream().map(SkillOptionMapper::toDto).toList();
    }

    private UpdateSkillOptionRequestDTO convertToDTO(SkillOption skillOption) {
        return UpdateSkillOptionRequestDTO.builder()
                .skillId(skillOption.getId())
                .name(skillOption.getName())
                .build();
    }


}
