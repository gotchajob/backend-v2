package com.example.gcj.service.impl;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.*;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.model.User;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.repository.UserRepository;
import com.example.gcj.service.ExpertNationSupportService;
import com.example.gcj.service.ExpertSkillOptionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.EmailService;
import com.example.gcj.util.JwtUtil;
import com.example.gcj.util.Util;
import com.example.gcj.util.mapper.ExpertMapper;
import com.example.gcj.util.mapper.UserMapper;
import com.example.gcj.util.status.ExpertRegisterRequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    final static int MENTOR_ROLE = 3;
    final static int USER_ROLE = 4;
    final static int DEFAULT_MENTOR_STATUS = 2;
    final static String DEFAULT_PASSWORD = "default";

    final static int BAN_STATUS = 0;
    final static int UNBAN_STATUS = 1;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwt;

    private final ExpertSkillOptionService expertSkillOptionService;
    private final ExpertNationSupportService expertNationSupportService;
    private final EmailService emailService;

    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    private final SearchRepository searchRepository;


    @Override
    public LoginResponseDTO login(LoginRequestDTO userLogin) {
        if (userLogin == null) {

        }

        if(userLogin == null || userLogin.getEmail() == null || userLogin.getPassword() == null){
            throw new CustomException("LOGIN_FAIL");
        }
        User user = userRepository.getUserByEmail(userLogin.getEmail());
        if (user == null || !bCryptPasswordEncoder.matches(userLogin.getPassword(), user.getPassword()))  {
            throw new CustomException("Invalid user name or password");
        }

        if (user.getStatus() == 0) {
            throw new CustomException("User is banned");
        }

        if (user.getStatus() == 2) {
            throw new CustomException("User is not verify");
        }


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

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
        userRepository.save(user);
    }

    @Override
    public void createExpertAccount(CreateExpertAccountRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }



        }

        boolean isExistEmail = userRepository.existsByEmail(expertRegisterRequest.getEmail());
        if (isExistEmail) {
            throw new CustomException("email is existed!");
        }

        User user = User.builder()
                .email(expertRegisterRequest.getEmail())
                .avatar(request.getAvatar())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(DEFAULT_PASSWORD)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(DEFAULT_MENTOR_STATUS)
                .roleId(MENTOR_ROLE)
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
                .yearExperience(request.getYearExperience())
                .status(1)
                .build();
        expert.setUser(_user);
        Expert _expert = expertRepository.save(expert);

        expertSkillOptionService.createExpertSkillOption(_expert.getId(), request.getExpertSKillOptionList());
        expertNationSupportService.create(_expert.getId(), request.getNationSupport());

        expertRegisterRequest.setExpertId(_expert.getId());
        expertRegisterRequest.setStatus(ExpertRegisterRequestStatus.WAIT_TO_APPROVE_FORM);
        expertRegisterRequestRepository.save(expertRegisterRequest);
    }

    @Override
    public PageResponseDTO<ExpertAccountResponse> getExpertAccountNotVerify(int page, int limit) {
        Pageable pageable = PageRequest.of(page-1, limit);

        Page<User> experts = userRepository.getUserByStatusAndRoleId(DEFAULT_MENTOR_STATUS, MENTOR_ROLE, pageable);

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
        if (user.getRoleId() != MENTOR_ROLE) {
            throw new CustomException("Account is not expert!");
        }
        if (user.getStatus() != DEFAULT_MENTOR_STATUS) {
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
    public void rejectExpert(long expertId, RejectExpertDTO request) {
        User user = userRepository.getUserById(expertId);
        if (user == null) {
            throw new CustomException("User not found");
        }
        user.setStatus(3);
        ExpertRegisterRequest expertRegisterRequest = expertRegisterRequestRepository.getById(request.getId());
        if (expertRegisterRequest == null) {
            throw new CustomException("Mentor register request not found!");
        }
        expertRegisterRequest.setUrl(request.getUrl());
        expertRegisterRequest.setStatus(4);
        expertRegisterRequest.setNote(request.getNote());
        expertRegisterRequestRepository.save(expertRegisterRequest);

        emailService.sendEmailRejectExpertRequest(expertRegisterRequest.getEmail(), request.getNote(), request.getUrl() );
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

        return userRepository.getUserByEmail(email);
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
        String body = "Dear "+fullName + ",\n" +
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
