package com.example.gcj.service.impl;

import com.example.gcj.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.model.SkillOption;
import com.example.gcj.repository.SkillOptionRepository;
import com.example.gcj.service.SkillOptionService;
import com.example.gcj.util.mapper.SkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SkillOptionServiceImpl implements SkillOptionService {
    private final SkillOptionRepository skillOptionRepository;

    @Override
    public List<SkillOptionResponseDTO> getAll() {
        List<SkillOption> skillOptionList = skillOptionRepository.findAll();
        return skillOptionList.stream().map(SkillOptionMapper::toDto).toList();
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
            Optional<SkillOption> existingSkillOption = skillOptionRepository.findById(dto.getSkillId());
            if(existingSkillOption.isPresent()) {
                SkillOption existing = existingSkillOption.get();
                existing.setName(dto.getName());
                skillOptionRepository.save(existing);
                updateSkillOptionList.add(convertToDTO(existing));
            }
        }
        return updateSkillOptionList;
    }


    @Override
    public boolean deleteSkillOptions(List<Long> skillIds) {
        List<SkillOption> skillOptionsToDelete = skillOptionRepository.findAllById(skillIds);
        if (!skillOptionsToDelete.isEmpty()) {
            skillOptionRepository.deleteAll(skillOptionsToDelete);
            return true;
        } else {
            return false;
        }

    }

    private UpdateSkillOptionRequestDTO convertToDTO(SkillOption skillOption) {
        return UpdateSkillOptionRequestDTO.builder()
                .skillId(skillOption.getId())
                .name(skillOption.getName())
                .build();
    }


}
