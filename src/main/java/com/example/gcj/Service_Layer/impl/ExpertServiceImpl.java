package com.example.gcj.Service_Layer.impl;

import com.example.gcj.External_Service.EmailService;
import com.example.gcj.Repository_Layer.model.Expert;
import com.example.gcj.Repository_Layer.model.ExpertNationSupport;
import com.example.gcj.Repository_Layer.model.ExpertSkillOption;
import com.example.gcj.Repository_Layer.model.User;
import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertDescriptionRequestDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertProfileRequestDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.ExpertAccountResponse;
import com.example.gcj.Service_Layer.service.*;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Shared.util.Util;
import com.example.gcj.Service_Layer.mapper.ExpertMapper;
import com.example.gcj.Service_Layer.mapper.ExpertNationSupportMapper;
import com.example.gcj.Service_Layer.mapper.ExpertSkillOptionMapper;
import com.example.gcj.Shared.util.status.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PolicyService policyService;
    private final ExpertNationSupportService expertNationSupportService;
    private final ExpertSkillOptionService expertSkillOptionService;
    private final EmailService emailService;
    private final UserService userService;

    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final ExpertNationSupportRepository expertNationSupportRepository;
    private final ExpertRepository expertRepository;
    private final SearchRepository searchRepository;
    private final UserRepository userRepository;

    @Override
    public List<ExpertMatchListResponseDTO> expertMatch(Integer main, List<Long> skillOptionIds, List<String> nations, int yearExperience) {
        final int nationPoint = policyService.getByKey(PolicyKey.EXPERT_NATION_SUPPORT_POINT, Integer.class);
        final int yearExperiencePoint = policyService.getByKey(PolicyKey.EXPERT_YEAR_EXPERIENCE_POINT, Integer.class);
        final double expertYearExpertPointMaxFactor = policyService.getByKey(PolicyKey.EXPERT_YEAR_EXPERIENCE_MAX_FACTOR, Double.class);
        final int numberExpertMatch = policyService.getByKey(PolicyKey.NUMBER_EXPERT_MATCH, Integer.class);
        final double weightPersonalPoint = 3;
        final int personalPoint = 60;

        HashMap<Long, Double> expertPoints = new HashMap<>();
        if (main == null) {
            addNationSupportPoints(main, expertPoints, nations, nationPoint);
            addSkillOptionPoints(main, expertPoints, skillOptionIds);
            addYearExperiencePoints(main, expertPoints, yearExperience, yearExperiencePoint, expertYearExpertPointMaxFactor);
        } else if (main == 1) {
            addNationSupportPoints(main, expertPoints, nations, nationPoint);
            addSkillOptionPoints(main, expertPoints, skillOptionIds);
            addYearExperiencePoints(main, expertPoints, yearExperience, yearExperiencePoint, expertYearExpertPointMaxFactor);
        } else if (main == 2) {
            addSkillOptionPoints(main, expertPoints, skillOptionIds);
            addNationSupportPoints(main, expertPoints, nations, nationPoint);
            addYearExperiencePoints(main, expertPoints, yearExperience, yearExperiencePoint, expertYearExpertPointMaxFactor);
        } else if (main == 3) {
            addYearExperiencePoints(main, expertPoints, yearExperience, yearExperiencePoint, expertYearExpertPointMaxFactor);
            addNationSupportPoints(main, expertPoints, nations, nationPoint);
            addSkillOptionPoints(main, expertPoints, skillOptionIds);
        }

        return getResultList(expertPoints, numberExpertMatch);
    }

    @Override
    public List<ExpertMatchListResponseDTO> newExpertMatch(Integer main, List<Long> skillOptionIds, List<String> nations, Integer yearExperience) {
        final double weightNation = policyService.getByKey(PolicyKey.EXPERT_NATION_SUPPORT_POINT, Double.class);
        final double weightExperience = policyService.getByKey(PolicyKey.EXPERT_YEAR_EXPERIENCE_POINT, Double.class);
        final double weightSkill = policyService.getByKey(PolicyKey.EXPERT_SKILL_POINT, Double.class);
        final double weightPersonalPoint = policyService.getByKey(PolicyKey.EXPERT_PERSONAL_POINT, Double.class);
        final int personalPoint = policyService.getByKey(PolicyKey.EXPERT_PERSONAL_POINT_MIN_TO_MATCHING, Integer.class);;

        final int numberExpertMatch = policyService.getByKey(PolicyKey.NUMBER_EXPERT_MATCH, Integer.class);

        Pageable pageable = PageRequest.of(0, numberExpertMatch);

        List<ExpertMatchListResponseDTO> matchingExperts = expertRepository.findMatchingExperts(
                yearExperience, nations, skillOptionIds, personalPoint, weightExperience,
                weightNation, weightSkill, weightPersonalPoint, main, pageable);

        for (ExpertMatchListResponseDTO matchingExpert : matchingExperts) {
            List<ExpertNationSupportResponseDTO> _expertNationSupport = expertNationSupportRepository
                    .findByExpertId(matchingExpert.getExpertId()).stream().map(ExpertNationSupportMapper::toDto).toList();
            List<ExpertSkillOptionResponseDTO> _expertSkillOptions = expertSkillOptionRepository
                    .findByExpertId(matchingExpert.getExpertId()).stream().map(ExpertSkillOptionMapper::toDto).toList();

            matchingExpert.setSkills(_expertSkillOptions);
            matchingExpert.setNationSupport(_expertNationSupport);
        }

        return matchingExperts;
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

    @Override
    public ExpertAccountResponse getByCurrent() {
        User user = userService.currentUser();
        if (user == null) {
            throw new CustomException("user not found!");
        }

        return ExpertMapper.toDto(user);
    }

    @Override
    public boolean updateExpert(long id, UpdateExpertRequestDTO request) {
        Expert expert = expertRepository.getById(id);
        if (expert == null) {
            throw new CustomException("not found expert with id " + id);
        }

        User user = expert.getUser();
        if (user == null) {
            throw new CustomException("not found user!");
        }


        expert.setBio(request.getBio());
        expert.setEmailContact(request.getEmail());
        expert.setPortfolioUrl(request.getPortfolioUrl());
        expert.setFacebookUrl(request.getFacebookUrl());
        expert.setTwitterUrl(request.getTwitterUrl());
        expert.setLinkedinUrl(request.getLinkedInUrl());
        expert.setEducation(request.getEducation());
        expert.setYearExperience(request.getYearExperience());
        expert.setBirthDate(request.getBirthDate());
        expertRepository.save(expert);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAvatar(request.getAvatar());
        userRepository.save(user);

        expertNationSupportService.deleteAllByExpertId(id);
        expertNationSupportService.create(id, request.getNationSupport());

        expertSkillOptionService.deleteAllByExpertId(id);
        expertSkillOptionService.createExpertSkillOption(id, request.getExpertSKillOptionList());

        return true;
    }

    @Override
    public boolean verifyExpert(long expertId) {
        Expert expert = expertRepository.getById(expertId);
        if (expert == null) {
            throw new CustomException("not found expert with id" + expertId);
        }
        User user = expert.getUser();
        if (user == null) {
            throw new CustomException("user not found");
        }

        user.setStatus(UserStatus.ACTIVE);
        String password = Util.generateRandomPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

        expert.setStatus(1);
        expertRepository.save(expert);

        String fullName = user.getFirstName() + " " + user.getLastName();
        sendEmailApproveExpert(user.getEmail(), password, fullName);
        return true;
    }

    @Override
    public long getCurrentExpertId() {
        long userId = userService.getCurrentUserId();
        Long expertId = expertRepository.getIdByUserId(userId);
        if (expertId == null) {
            throw new CustomException("not found current expert");
        }

        return expertId;
    }

    @Override
    public String getEmailByExpertId(long expertId) {
        Expert expert = expertRepository.getById(expertId);
        return expert.getUser().getEmail();
    }

    @Override
    public boolean updatePrice(long expertId, double cost) {
        Expert expert = expertRepository.getById(expertId);
        if (expert == null) {
            throw new CustomException("not found expert with id " + expertId);
        }

        checkExpertCost(cost);

        expert.setCost(cost);
        expertRepository.save(expert);

        return true;
    }

    @Override
    @Transactional
    public boolean updateExpertPoint(long expertId, int point) {
        Expert expert = get(expertId);

        int newExpertPoint = expert.getPersonalPoint() + point;
        int expertPersonalPointMin = policyService.getByKey(PolicyKey.EXPERT_PERSONAL_POINT_MIN, Integer.class);
        int expertPersonalPointMax = policyService.getByKey(PolicyKey.EXPERT_PERSONAL_POINT_MAX, Integer.class);

        if (newExpertPoint < expertPersonalPointMin) {
            newExpertPoint = expertPersonalPointMin;
        }
        if (newExpertPoint > expertPersonalPointMax) {
            newExpertPoint = expertPersonalPointMax;
        }

        expert.setPersonalPoint(newExpertPoint);
        expertRepository.save(expert);

        if (newExpertPoint == expertPersonalPointMin) {
            User user = expert.getUser();
            if (user == null) {
                throw new CustomException("not found user");
            }

            user.setStatus(UserStatus.DISABLE);
            userRepository.save(user);
        }

        return true;
    }

    @Override
    public boolean updateProfile(long expertId, UpdateExpertProfileRequestDTO request) {
        Expert expert = get(expertId);

        expert.setBio(request.getBio());
        expert.setBirthDate(request.getBirthDate());
        expert.setPortfolioUrl(request.getPortfolioUrl());
        expert.setEmailContact(request.getEmailContact());
        expert.setLinkedinUrl(request.getLinkedInUrl());
        expert.setFacebookUrl(request.getFacebookUrl());
        expert.setTwitterUrl(request.getTwitterUrl());
        expert.setBackgroundImage(request.getBackgroundImage());
        expert.setEducation(request.getEducation());
        expert.setYearExperience(request.getYearExperience());
        expertRepository.save(expert);

        User user = expert.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAvatar(request.getAvatar());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean updateCurrentExpertStatus(int status) {
        String currentUserEmail = userService.currentUserEmail();

        Expert expert = expertRepository.getByEmail(currentUserEmail);
        expert.setStatus(status);
        expertRepository.save(expert);

        return true;
    }

    @Override
    public boolean updateDescription(long expertId, UpdateExpertDescriptionRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        Expert expert = get(expertId);
        expert.setShortDescription(request.getShortDescription());
        expertRepository.save(expert);

        return true;
    }

    private void checkExpertCost(double cost) {
        Double expertCostMin = policyService.getByKey(PolicyKey.EXPERT_COST_MIN, Double.class);
        Double expertCostMax = policyService.getByKey(PolicyKey.EXPERT_COST_MAX, Double.class);

        if (cost < expertCostMin || cost > expertCostMax) {
            throw new CustomException("expert cost must " + expertCostMin + " to " + expertCostMax);
        }

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

    private void addYearExperiencePoints(Integer main, HashMap<Long, Double> expertPoints, int yearExperience, int yearExperiencePoint, double expertYearExpertPointMaxFactor) {
        //expert year experience. expertYear/yearExperience * point (max is 2x point)
        if (yearExperience > 0) {
            List<Expert> experts = null;
            if (main == null || main == 3) {
                experts = expertRepository.findByYearExperienceGreaterThanEqualAndStatus(yearExperience, Status.ACTIVE);
            } else {
                List<Long> expertIds = new ArrayList<>(expertPoints.keySet());
                if (!expertIds.isEmpty()) {
                    experts = expertRepository.findByYearExperienceGreaterThanEqualAndStatusAndIdIn(yearExperience, Status.ACTIVE, expertIds);
                }
             }

            if (experts != null && !experts.isEmpty()) {
                for (Expert expert : experts) {
                    double expertPoint = ((double) expert.getYearExperience() / yearExperience) * yearExperiencePoint;
                    expertPoint = Math.min(yearExperiencePoint * expertYearExpertPointMaxFactor, expertPoint);
                    addPoint(expertPoints, expert.getId(), expertPoint);
                }
            }
        }
    }

    private void addSkillOptionPoints(Integer main, HashMap<Long, Double> expertPoints, List<Long> skillOptionIds) {
        if (skillOptionIds != null && !skillOptionIds.isEmpty()) {
            List<Long> expertIds = new ArrayList<>(expertPoints.keySet());
            for (long skillOptionId : skillOptionIds) {
                List<Object[]> results = null;
                if (main == null || main == 2) {
                     results = expertSkillOptionRepository.findExpertSkillOptionsWithRatingStatsBySkillOptionId(skillOptionId);
                } else if (!expertIds.isEmpty()) {
                    results = expertSkillOptionRepository.findExpertSkillOptionsWithRatingStatsBySkillOptionIdAndExpertIdIn(skillOptionId, expertIds);
                }

                if (results == null || results.isEmpty()) {
                    continue;
                }

                for (Object[] result : results) {
                    ExpertSkillOption expertSkillOption = (ExpertSkillOption) result[0];
                    Long sumPoints = Objects.requireNonNullElse((Long) result[1], 0L);
                    Long ratingCount = Objects.requireNonNullElse((Long) result[2], 0L);
                    double expertPoint = (double) (sumPoints) / (ratingCount);
                    addPoint(expertPoints, expertSkillOption.getExpertId(), expertPoint);
                }
            }
        }

    }

    private void addNationSupportPoints(Integer main, HashMap<Long, Double> expertPoints, List<String> nations, int nationPoint) {
        //expert nation support
        if (nations != null && !nations.isEmpty()) {
            List<ExpertNationSupport> expertNationSupports = null;
            if (main == null || main == 1) {
                expertNationSupports = expertNationSupportRepository.findAllByNationIn(nations);
            } else {
                List<Long> expertIds = new ArrayList<>(expertPoints.keySet());
                if (expertIds != null && !expertIds.isEmpty()) {
                    expertNationSupports = expertNationSupportRepository.findAllByNationInAndExpertIdInAndStatusNot(nations, expertIds, 0);
                }
            }

            if (expertNationSupports != null && !expertNationSupports.isEmpty()) {
                for (ExpertNationSupport expertNationSupport : expertNationSupports) {
                    addPoint(expertPoints, expertNationSupport.getExpertId(), nationPoint);
                }
            }
        }
    }

    private void sendEmailApproveExpert(String email, String password, String fullName) {
        String subject = "Yêu Cầu Phê Duyệt Để Trở Thành Chuyên Gia trên GotchaJob";
        String body = "Kính gửi " + fullName + ",\n" +
                "\n" +
                "Hy vọng bạn nhận được email này trong tình trạng tốt.\n" +
                "\n" +
                "Tôi viết thư này để chính thức yêu cầu phê duyệt trở thành chuyên gia trên GotchaJob"+
                "\n" +
                "Để tiến hành quy trình này, tôi đã tạo tài khoản trên GotchaJob với thông tin đăng nhập sau:\n" +
                "\n" +
                "Tên đăng nhập: " + email + "\n" +
                "Mật khẩu: " + password +
                "\n" +
                "Trân trọng,\n" +
                "\n" +
                "[GotchaJob]\n";

        emailService.sendEmail(email, subject, body);
    }

    private Expert get(long id) {
        Expert expert = expertRepository.getById(id);
        if (expert == null) {
            throw new CustomException("not found expert with id " + id);
        }

        return expert;
    }
}
