package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Skill;
import com.example.gcj.Repository_Layer.repository.CategoryRepository;
import com.example.gcj.Repository_Layer.repository.SkillRepository;
import com.example.gcj.Service_Layer.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Service_Layer.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.Service_Layer.mapper.SkillMapper;
import com.example.gcj.Service_Layer.service.SkillOptionService;
import com.example.gcj.Service_Layer.service.SkillService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final CategoryRepository categoryRepository;
    private final SkillOptionService skillOptionService;

    @Override
    public List<SkillResponseDTO> getAll(Long categoryId) {
        List<Skill> skillList = skillRepository.findAll(categoryId);
        return skillList.stream().map(SkillMapper::toDto).toList();
    }

    @Override
    public List<SkillResponseDTO> findSkillByCategoryId(long categoryId) {
        List<Skill> skillList = skillRepository.findAllByCategoryId(categoryId);
        return skillList.stream().map(SkillMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void createSkill(CreateSkillRequestDTO request) {
        if(request == null) {
            throw new CustomException("bad request");
        }
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new CustomException("not found category with id " + request.getCategoryId());
        }

        Skill skill = Skill.builder()
                .categoryId(request.getCategoryId())
                .name(request.getName())
                .status(1)
                .build();
        skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(long id) {
        Skill skill = get(id);

        skill.setStatus(0);
        skillRepository.save(skill);

        skillOptionService.deleteBySkillId(skill.getId());
    }

    @Override
    public boolean deleteSkillByCategoryId(long categoryId) {
        List<Skill> list = skillRepository.findAllByCategoryId(categoryId);
        for (Skill skill : list) {
            skill.setStatus(0);

            skillOptionService.deleteBySkillId(skill.getId());
        }
        skillRepository.saveAll(list);

        return true;
    }

    @Override
    public boolean update(long id, UpdateSkillRequestDTO request) {
        Skill skill = get(id);

        skill.setName(request.getSkillName());
        skillRepository.save(skill);

        return true;
    }

    private Skill get(long id) {
        Skill skill = skillRepository.findById(id);
        if (skill == null) {
            throw new CustomException("not found skill with id " + id);
        }

        return skill;
    }



}
