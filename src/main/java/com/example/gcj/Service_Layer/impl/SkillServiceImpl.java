package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Skill;
import com.example.gcj.Repository_Layer.repository.SkillRepository;
import com.example.gcj.Service_Layer.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Service_Layer.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.Service_Layer.service.SkillService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<UpdateSkillRequestDTO> updateSkill(List<UpdateSkillRequestDTO> request) {
        List<UpdateSkillRequestDTO> updateSkillList = new ArrayList<>();
        for (UpdateSkillRequestDTO dto : request) {
            Optional<Skill> existingSkill = skillRepository.findById(dto.getSkillId());
            if(existingSkill.isPresent()) {
                Skill existing = existingSkill.get();
                existing.setName(dto.getSkillName());
                existing.setCategoryId(dto.getCategoryId());
                skillRepository.save(existing);
                updateSkillList.add(convertToDTO(existing));
            }
        }
        return updateSkillList;
    }

//    @Override
//    public void updateSkill(UpdateSkillRequestDTO request) {
//        Optional<Skill> existingSkillList = skillRepository.findById(id);
//        if(!existingSkillList.isPresent()) {
//            throw new CustomException("Skill not found with id" + id);
//        }
//
//        Skill existingSkill = existingSkillList.get();
//        existingSkill.setCategoryId(request.getCategoryId());
//        existingSkill.setName(request.getSkillName());
//
//        skillRepository.save(existingSkill);
//    }

    @Override
    public void deleteSkill(long id) {
        if(!skillRepository.existsById(id)) {
            throw new CustomException("Skill not found with id" + id);
        }
        skillRepository.deleteById(id);
    }

    private UpdateSkillRequestDTO convertToDTO(Skill skill) {
        return UpdateSkillRequestDTO.builder()
                .skillId(skill.getId())
                .skillName(skill.getName())
                .categoryId(skill.getCategoryId())
                .build();
    }
}
