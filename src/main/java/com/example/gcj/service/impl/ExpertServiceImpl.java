package com.example.gcj.service.impl;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertNationSupport;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.repository.ExpertNationSupportRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.mapper.ExpertMapper;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;
import com.example.gcj.util.mapper.ExpertSkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final ExpertNationSupportRepository expertNationSupportRepository;
    private final ExpertRepository expertRepository;

    @Override
    public List<ExpertMatchListResponseDTO> expertMatch(Long categoryId, List<Long> skillOptionIds, List<String> nations, int yearExperience) {
        int nationPoint = 2;
        int yearExperiencePoint = 3;
        int skillOptionPoint = 3;
        HashMap<Long, Integer> listExpert = new HashMap<>();
        List<ExpertMatchListResponseDTO> response = new ArrayList<>();

        //expert nation support
        List<ExpertNationSupport> expertNationSupports = expertNationSupportRepository.findAllByNationIn(nations);
        if (!expertNationSupports.isEmpty()) {
            for (ExpertNationSupport expertNationSupport: expertNationSupports) {
                addPoint(listExpert, expertNationSupport.getExpertId(), nationPoint);
            }
        }

        //expert skill option
        List<ExpertSkillOption> expertSkillOptions = expertSkillOptionRepository.findAllBySkillOptionIdInAndStatus(skillOptionIds, 1);
        if (!expertSkillOptions.isEmpty()) {
            for (ExpertSkillOption expertSkillOption: expertSkillOptions) {
                addPoint(listExpert, expertSkillOption.getExpertId(), skillOptionPoint);
            }
        }

        //expert year experience
        List<Expert> experts = expertRepository.findAllByYearExperienceAfter(yearExperience);
        if (!experts.isEmpty()) {
            for (Expert expert : experts) {
                addPoint(listExpert, expert.getId(), yearExperiencePoint);
            }
        }

        // Get top 5 experts with largest points
        List<Map.Entry<Long, Integer>> top5Experts = listExpert.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sorting in descending order
                .limit(5)
                .collect(Collectors.toList());

        // Print the top 5 experts
        for (Map.Entry<Long, Integer> entry : top5Experts) {
            Expert expert = expertRepository.getById(entry.getKey());
            if (expert == null) {
                continue;
            }

            ExpertMatchListResponseDTO _response = ExpertMapper.toDto(expert, entry.getValue());

            List<ExpertNationSupportResponseDTO> _expertNationSupport = expertNationSupportRepository
                    .findByExpertId(entry.getKey()).stream().map(ExpertNationSupportMapper::toDto).toList();
            List<ExpertSkillOptionResponseDTO> _expertSkillOptions = expertSkillOptionRepository
                    .findByExpertId(entry.getKey()).stream().map(ExpertSkillOptionMapper::toDto).toList();

            _response.setSkills(_expertSkillOptions);
            _response.setNationSupport(_expertNationSupport);

            response.add(_response);
        }

        return response;
    }

    private void addPoint(HashMap<Long, Integer> expertList, long id, int point) {
        if (expertList.containsKey(id)) {
            expertList.put(id, expertList.get(id) + point);
        } else {
            expertList.put(id, point);
        }
    }
}
