package com.example.gcj.service.impl;

import com.example.gcj.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Skill;
import com.example.gcj.repository.SkillRepository;
import com.example.gcj.service.SkillService;
import com.example.gcj.util.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;


    @Override
    public List<SkillResponseDTO> getAll() {
        List<Skill> skillList = skillRepository.findAll();
        return skillList.stream().map(SkillMapper::toDto).toList();
    }

    @Override
    public List<SkillResponseDTO> findSkillByCategoryId(long catogoryId) {
        List<Skill> skillList = skillRepository.findAllByCategoryId(catogoryId);
        return skillList.stream().map(SkillMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void createSkill(CreateSkillRequestDTO request) {
        if(request == null) {
            throw new CustomException("Null request");
        }
        Skill skill = Skill.builder()
                .categoryId(request.getCategoryId())
                .name(request.getName())
                .build();
        skillRepository.save(skill);
    }

    @Override
    public void updateSkill(long id, UpdateSkillRequestDTO request) {
        Optional<Skill> existingSkillList = skillRepository.findById(id);
        if(!existingSkillList.isPresent()) {
            throw new CustomException("Skill not found with id" + id);
        }

        Skill existingSkill = existingSkillList.get();
        existingSkill.setCategoryId(request.getCategoryId());
        existingSkill.setName(request.getName());

        skillRepository.save(existingSkill);
    }

    @Override
    public void deleteSkill(long id) {
        if(!skillRepository.existsById(id)) {
            throw new CustomException("Skill not found with id" + id);
        }
        skillRepository.deleteById(id);
    }
}
