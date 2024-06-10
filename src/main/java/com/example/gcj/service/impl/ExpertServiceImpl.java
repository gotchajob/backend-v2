package com.example.gcj.service.impl;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertNationSupport;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.repository.ExpertNationSupportRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.ExpertMapper;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;
import com.example.gcj.util.mapper.ExpertSkillOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final PolicyService policyService;

    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final ExpertNationSupportRepository expertNationSupportRepository;
    private final ExpertRepository expertRepository;
    private final SearchRepository searchRepository;

    @Override
    public List<ExpertMatchListResponseDTO> expertMatch(List<Long> skillOptionIds, List<String> nations, int yearExperience) {
        final int nationPoint = policyService.getByKey(PolicyKey.EXPERT_NATION_SUPPORT_POINT, Integer.class);
        final int yearExperiencePoint = policyService.getByKey(PolicyKey.EXPERT_YEAR_EXPERIENCE_POINT, Integer.class);
        final double expertYearExpertPointMaxFactor = policyService.getByKey(PolicyKey.EXPERT_YEAR_EXPERIENCE_MAX_FACTOR, Double.class);
        final int numberExpertMatch = policyService.getByKey(PolicyKey.NUMBER_EXPERT_MATCH, Integer.class);

        HashMap<Long, Double> expertPoints = new HashMap<>();

        addNationSupportPoints(expertPoints, nations, nationPoint);

        // Add points for expert skill options
        addSkillOptionPoints(expertPoints, skillOptionIds);

        // Add points for expert year experience
        addYearExperiencePoints(expertPoints, yearExperience, yearExperiencePoint, expertYearExpertPointMaxFactor);

        return getResultList(expertPoints, numberExpertMatch);
    }

    @Override
    public PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<Expert> expertPage = searchRepository.getEntitiesPage(Expert.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(expertPage.stream().map(ExpertMapper::toDto).toList(), expertPage.getTotalPages());
    }

    @Override
    public ExpertAccountResponse getExpert(long id) {
        Expert expert = expertRepository.getById(id);
        if (expert == null) {
            throw new CustomException("Expert not found. id=" + id);
        }

        return ExpertMapper.toDto(expert);
    }

    private void addPoint(HashMap<Long, Double> expertPoints, long expertId, double point) {
        expertPoints.merge(expertId, point, Double::sum);
    }

    private List<ExpertMatchListResponseDTO> getResultList(HashMap<Long, Double> expertList, int numberExpertMatch) {
        List<Map.Entry<Long, Double>> topExperts = expertList.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sorting in descending order
                .collect(Collectors.toList());

        List<ExpertMatchListResponseDTO> response = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : topExperts) {
            Expert expert = expertRepository.getById(entry.getKey());
            boolean isInvalidExpert = expert == null || expert.getStatus() != 1 || expert.getUser() == null || expert.getUser().getStatus() != 1;
            if (isInvalidExpert) {
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

            if (response.size() >= numberExpertMatch) {
                break;
            }
        }

        return response;
    }
    private void addYearExperiencePoints(HashMap<Long, Double> expertPoints, int yearExperience, int yearExperiencePoint, double expertYearExpertPointMaxFactor) {
        //expert year experience. expertYear/yearExperience * point (max is 2x point)
        if (yearExperience > 0) {
            List<Expert> experts = expertRepository.findByYearExperienceGreaterThanEqualAndStatus(yearExperience, Status.ACTIVE);
            if (experts != null && !experts.isEmpty()) {
                for (Expert expert : experts) {
                    double expertPoint = ((double) expert.getYearExperience() / yearExperience) * yearExperiencePoint;
                    expertPoint = Math.min(yearExperiencePoint * expertYearExpertPointMaxFactor, expertPoint);
                    addPoint(expertPoints, expert.getId(), expertPoint);
                }
            }
        }
    }

    private void addSkillOptionPoints(HashMap<Long, Double> expertPoints, List<Long> skillOptionIds) {
        //expert skill option
        //todo: point by default point and rating
        if (skillOptionIds != null && !skillOptionIds.isEmpty()) {
            for (long skillOptionId : skillOptionIds) {
                List<Object[]> results = expertSkillOptionRepository.findExpertSkillOptionsWithRatingStatsBySkillOptionId(skillOptionId);
                if (results == null || results.isEmpty()) {
                    continue;
                }

                for (Object[] result : results) {
                    ExpertSkillOption expertSkillOption = (ExpertSkillOption) result[0];
                    Long sumPoints = Objects.requireNonNullElse((Long) result[1], 0L);
                    Long ratingCount = Objects.requireNonNullElse( (Long) result[2], 0L);
                    double expertPoint = (double) (sumPoints + expertSkillOption.getDefaultPoint()) / (ratingCount + 1);
                    addPoint(expertPoints, expertSkillOption.getExpertId(), expertPoint);
                }
            }
        }

    }

    private void addNationSupportPoints(HashMap<Long, Double> expertPoints, List<String> nations, int nationPoint) {
        //expert nation support
        if (nations != null && !nations.isEmpty()) {
            List<ExpertNationSupport> expertNationSupports = expertNationSupportRepository.findAllByNationIn(nations);
            if (expertNationSupports!= null && !expertNationSupports.isEmpty()) {
                for (ExpertNationSupport expertNationSupport : expertNationSupports) {
                    addPoint(expertPoints, expertNationSupport.getExpertId(), nationPoint);
                }
            }
        }
    }
}
