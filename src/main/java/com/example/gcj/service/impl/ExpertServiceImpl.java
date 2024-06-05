package com.example.gcj.service.impl;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertNationSupport;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.repository.ExpertNationSupportRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Util;
import com.example.gcj.util.mapper.ExpertMapper;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;
import com.example.gcj.util.mapper.ExpertSkillOptionMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.gcj.util.Regex.SORT_BY;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final ExpertNationSupportRepository expertNationSupportRepository;
    private final ExpertRepository expertRepository;
    private final SearchRepository searchRepository;

    @Override
    public List<ExpertMatchListResponseDTO> expertMatch(Long categoryId, List<Long> skillOptionIds, List<String> nations, int yearExperience) {
        int nationPoint = 2;
        int yearExperiencePoint = 3;
        int skillOptionPoint = 5;
        HashMap<Long, Integer> listExpert = new HashMap<>();
        List<ExpertMatchListResponseDTO> response = new ArrayList<>();

        //expert nation support
        if (!nations.isEmpty()) {
            List<ExpertNationSupport> expertNationSupports = expertNationSupportRepository.findAllByNationIn(nations);
            if (!expertNationSupports.isEmpty()) {
                for (ExpertNationSupport expertNationSupport: expertNationSupports) {
                    addPoint(listExpert, expertNationSupport.getExpertId(), nationPoint);
                }
            }
        }


        //expert skill option
        //todo: point by default point and rating
        if (!skillOptionIds.isEmpty()) {
            List<ExpertSkillOption> expertSkillOptions = expertSkillOptionRepository.findAllBySkillOptionIdInAndStatus(skillOptionIds, 1);
            if (!expertSkillOptions.isEmpty()) {
                for (ExpertSkillOption expertSkillOption: expertSkillOptions) {
                    addPoint(listExpert, expertSkillOption.getExpertId(), expertSkillOption.getDefaultPoint());
                }
            }
        }


        //expert year experience
        // todo: more year more point?
        if (yearExperience > 0) {
            List<Expert> experts = expertRepository.findAllByYearExperienceAfter(yearExperience);
            if (!experts.isEmpty()) {
                for (Expert expert : experts) {
                    addPoint(listExpert, expert.getId(), yearExperiencePoint);
                }
            }
        }

        //todo: check expert is verify?

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

    @Override
    public PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String filter) {
        List<Sort.Order> sorts = Util.sortConvert(sortBy);
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(sorts));

        Page<Expert> experts = expertRepository.findAll(pageable);
        return new PageResponseDTO<>(experts.map(ExpertMapper::toDto).toList(), experts.getTotalPages());
    }

    @Override
    public PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String... search) {
        return searchRepository.advanceSearchExpert(pageNumber, pageSize, sortBy, search);
    }

    private void addPoint(HashMap<Long, Integer> expertList, long id, int point) {
        if (expertList.containsKey(id)) {
            expertList.put(id, expertList.get(id) + point);
        } else {
            expertList.put(id, point);
        }
    }
}
