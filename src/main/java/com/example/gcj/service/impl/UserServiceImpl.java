package com.example.gcj.service.impl;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.*;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Customer;
import com.example.gcj.model.Expert;
import com.example.gcj.model.User;
import com.example.gcj.repository.CustomerRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.repository.UserRepository;
import com.example.gcj.repository.specification.ObjectSpecificationBuilder;
import com.example.gcj.service.ExpertNationSupportService;
import com.example.gcj.service.ExpertSkillOptionService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.JwtUtil;
import com.example.gcj.util.Regex;
import com.example.gcj.util.Util;
import com.example.gcj.util.mapper.ExpertMapper;
import com.example.gcj.util.mapper.UserMapper;
import com.example.gcj.util.service.EmailService;
import com.example.gcj.util.status.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    final static int EXPERT_ROLE = 3;
    final static int USER_ROLE = 4;
    final static int DEFAULT_EXPERT_STATUS = 2;
    final static String DEFAULT_PASSWORD = "default";

    final static int BAN_STATUS = 0;
    final static int UNBAN_STATUS = 1;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwt;

    private final ExpertSkillOptionService expertSkillOptionService;
    private final ExpertNationSupportService expertNationSupportService;
    private final EmailService emailService;
    private final PolicyService policyService;

    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    private final SearchRepository searchRepository;
    private final CustomerRepository customerRepository;


    @Override
    public LoginResponseDTO login(LoginRequestDTO userLogin) {
        if (userLogin == null) {
            throw new CustomException("invalid request");
        }

        if (!StringUtils.hasText(userLogin.getEmail()) || !StringUtils.hasText(userLogin.getPassword())) {
            throw new CustomException("invalid data request");
        }

        UserLoginDataResponseDTO user = userRepository.findByEmailDto(userLogin.getEmail());

        if (user == null || !bCryptPasswordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            throw new CustomException("invalid user name or password");
        }

        if (user.getStatus() == 0) {
            throw new CustomException("user is banned");
        }

        if (user.getStatus() == 2) {
            throw new CustomException("user is not verify");
        }
        if (user.getStatus() != 1) {
            throw new CustomException("user status invalid");
        }

        String token = jwt.generateToken(user.getEmail());
        return LoginResponseDTO.builder()
                .token(token)
                .roleId(user.getRoleId())
                .build();
    }


    @Override
    public PageResponseDTO<UserListResponseDTO> getAll(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<User> users = searchRepository.getEntitiesPage(User.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(users.map(UserMapper::toDto).toList(), users.getTotalPages());
    }


    @Override
    public void signup(SignupRequestDTO request) {
        if (request == null) {
            throw new CustomException("Null input");
        }

        User _user = userRepository.getUserByEmail(request.getEmail());
        if (_user != null) {
            throw new CustomException("Email is exist");
        }

        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodePassword, request.getFirstName(), request.getLastName());
        User save = userRepository.save(user);

        Customer customer = Customer
                .builder()
                .userId(save.getId())
                .numberBooking(0)
                .maxAllowCv(5)
                .build();
        customerRepository.save(customer);
    }

    @Override
    public long createExpertAccount(String email, CreateExpertAccountRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (!StringUtils.hasText(email)) {
            throw new CustomException("email don't has text");
        }

        boolean isExistEmail = userRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new CustomException("email is existed!");
        }

        int defaultPersonalPoint = policyService.getByKey(PolicyKey.DEFAULT_EXPERT_POINT, Integer.class);

        User user = User.builder()
                .email(email)
                .avatar(request.getAvatar())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(DEFAULT_PASSWORD)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(UserStatus.NOT_VERIFY)
                .roleId(EXPERT_ROLE)
                .build();
        User _user = userRepository.save(user);

        Expert expert = Expert
                .builder()
                .bio(request.getBio())
                .emailContact(request.getEmail())
                .birthDate(request.getBirthDate())
                .portfolioUrl(request.getPortfolioUrl())
                .facebookUrl(request.getFacebookUrl())
                .twitterUrl(request.getTwitterUrl())
                .linkedinUrl(request.getLinkedInUrl())
                .education(request.getEducation())
                .personalPoint(defaultPersonalPoint)
                .yearExperience(request.getYearExperience())
                .status(2)
                .build();
        expert.setUser(_user);
        Expert _expert = expertRepository.save(expert);

        expertSkillOptionService.createExpertSkillOption(_expert.getId(), request.getExpertSKillOptionList());
        expertNationSupportService.create(_expert.getId(), request.getNationSupport());

        return _expert.getId();
    }

    @Override
    public PageResponseDTO<ExpertAccountResponse> getExpertAccountNotVerify(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<User> experts = userRepository.getUserByStatusAndRoleId(DEFAULT_EXPERT_STATUS, EXPERT_ROLE, pageable);

        return new PageResponseDTO<>(experts.map(ExpertMapper::toDto).toList(), experts.getTotalPages());
    }

    @Override
    public void updateExpertStatus(Long id, int status) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("User not found");
        }
        if (status != 0 && status != 1) {

        }
        if (user.getRoleId() != EXPERT_ROLE) {
            throw new CustomException("Account is not expert!");
        }
        if (user.getStatus() != DEFAULT_EXPERT_STATUS) {
            throw new CustomException("Account verified!");
        }

        String fullName = user.getFirstName() + " " + user.getLastName();
        if (status == 0) {
            sendEmailRejectExpert(user.getEmail(), fullName);
        }

        if (status == 1) {
            String password = Util.generateRandomPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));

            sendEmailApproveExpert(user.getEmail(), password, fullName);
        }

        user.setStatus(status);
        userRepository.save(user);

    }

    @Override
    public void banUser(long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("Not found");
        }

        user.setStatus(BAN_STATUS);
        userRepository.save(user);
    }

    @Override
    public void unbanUser(long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("Not found");
        }

        user.setStatus(UNBAN_STATUS);
        userRepository.save(user);
    }

    @Override
    public User currentUser() {
        String email = currentUserEmail();
        if (email == null) {
            return null;
        }

        return userRepository.findByEmail(email);
    }

    @Override
    public String currentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ExpertAccountResponse getExpert(long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("User not found. id=" + id);
        }
        if (user.getStatus() == BAN_STATUS) {
            throw new CustomException("User is not banned!");
        }

        if (user.getExpert() == null) {
            throw new CustomException("User not Expert role");
        }

        return ExpertMapper.toDto(user);
    }

    @Override
    public boolean isExistEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new CustomException("invalid input");
        }

        return userRepository.existsByEmail(email);
    }

    @Override
    public long getCurrentExpertId() {
        User user = currentUser();
        if (user == null) {
            throw new CustomException("user not found!");
        }

        Expert expert = user.getExpert();
        if (expert == null) {
            throw new CustomException("user is not expert!");
        }

        return expert.getId();
    }

    @Override
    public long getCurrentUserId() {
        String email = currentUserEmail();
        Long userId = userRepository.getUserIdByEmail(email);
        if (userId == null) {
            throw new CustomException("not found user with email " + email);
        }

        return userId;
    }

    @Override
    public PageResponseDTO<UserListResponseDTO> getByUserAndExpert(Pageable pageable, String[] user, String[] expert) {
        Specification<User> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%user%"));

        ObjectSpecificationBuilder builder = new ObjectSpecificationBuilder();
        if (user != null) {
            for (String s: user) {
                Pattern pattern = Pattern.compile(Regex.SEARCH_SPEC_OPERATOR);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    builder.with(matcher.group(1), matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                }
            }
        }

        Page<User> users = userRepository.findAll(builder.build(), pageable);
        return new PageResponseDTO<>(users.map(UserMapper::toDto).toList(), users.getTotalPages());
    }

    @Override
    public boolean changePassword(ChangePasswordRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        User user = currentUser();
        if (user == null) {
            throw new CustomException("not found current user");
        }

        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new CustomException("old password not match");
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return true;
    }

    private void sendEmailApproveExpert(String email, String password, String fullName) {
        String subject = "Approval Request to Become a Expert on Gotchajob";
        String body = "Dear " + fullName + ",\n" +
                "\n" +
                "I hope this email finds you well.\n" +
                "\n" +
                "I am reaching out to formally request approval to become a expert on Gotchajob, an esteemed platform that fosters growth and development within the professional community. As someone passionate about [mention your area of expertise or field], I am eager to contribute my knowledge and skills to support aspiring individuals in their career journeys.\n" +
                "\n" +
                "To facilitate this process, I have created an account on Gotchajob with the following login credentials:\n" +
                "\n" +
                "Username: " + email + "\n" +
                "Password: " + password +
                "\n" +
                "I am committed to upholding the values and standards of Gotchajob and to providing valuable expertship to those in need. I am confident that my contributions will positively impact the community and help individuals achieve their career aspirations.\n" +
                "\n" +
                "Thank you for considering my request. I look forward to the opportunity to serve as a expert on Gotchajob and make a meaningful difference in the lives of others.\n" +
                "\n" +
                "Warm regards,\n" +
                "\n" +
                "[GotchaJob]\n";

        emailService.sendEmail(email, subject, body);
    }

    private void sendEmailRejectExpert(String email, String fullName) {
        String subject = "Rejection Notification for Expertship Application on Gotchajob";
        String body = "Dear " + fullName + ",\n" +
                "\n" +
                "I hope this message finds you well.\n" +
                "\n" +
                "I am writing to inform you that unfortunately, my application to become a expert on Gotchajob has been declined. While I am naturally disappointed by this outcome, I respect the decision made by the selection committee.\n" +
                "\n" +
                "Although I won't have the opportunity to contribute as a expert at this time, I remain deeply committed to supporting individuals in their professional growth and development. I will continue to seek out avenues where I can share my expertise and provide guidance to those in need.\n" +
                "\n" +
                "I appreciate the time and consideration given to my application. Should there be any feedback or suggestions for improvement, I am open to receiving them as they would help me enhance my qualifications for future opportunities.\n" +
                "\n" +
                "Thank you once again for considering my application.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "GotchaJob\n";

        emailService.sendEmail(email, subject, body);
    }
}
