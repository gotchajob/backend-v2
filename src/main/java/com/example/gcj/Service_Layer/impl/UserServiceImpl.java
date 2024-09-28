package com.example.gcj.Service_Layer.impl;

import com.example.gcj.External_Service.EmailService;
import com.example.gcj.Repository_Layer.model.*;
import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Repository_Layer.specification.ObjectSpecificationBuilder;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.CreateStaffRequestDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffListResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.UpdateStaffRequestDTO;
import com.example.gcj.Service_Layer.dto.user.*;
import com.example.gcj.Service_Layer.mapper.ExpertMapper;
import com.example.gcj.Service_Layer.mapper.UserMapper;
import com.example.gcj.Service_Layer.service.*;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.JwtUtil;
import com.example.gcj.Shared.util.Regex;
import com.example.gcj.Shared.util.Util;
import com.example.gcj.Shared.util.status.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
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
    private static final int STAFF_ROLE = 2;


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
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final StaffRepository staffRepository;



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

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .status(UserStatus.NOT_VERIFY)
                .roleId(4)
                .build();
        User save = userRepository.save(user);

        Integer defaultMaxAllowCv = policyService.getByKey(PolicyKey.DEFAULT_MAX_ALLOW_CV, Integer.class);
        Customer customer = Customer
                .builder()
                .userId(save.getId())
                .maxAllowCv(defaultMaxAllowCv)
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
        double expertCostMin = policyService.getByKey(PolicyKey.EXPERT_COST_MIN, Double.class);

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
                .cost(expertCostMin)
                .certification(request.getCertification())
                .build();
        expert.setUser(_user);
        Expert _expert = expertRepository.save(expert);

        expertSkillOptionService.createExpertSkillOption(_expert.getId(), request.getExpertSKillOptionList());
        expertNationSupportService.create(_expert.getId(), request.getNationSupport());

        return _expert.getId();
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

    @Override
    public boolean updateProfile(UpdateUserProfileRequestDTO request) {
        User user = currentUser();

        user.setAddress(request.getAddress());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAvatar(request.getAvatar());
        user.setPhone(request.getPhone());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean createVerifyEmail(String email) {
        String code = Util.generateCode();
        int expireTime = 30;

        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new CustomException("not found user with email " + email);
        }
        if (user.getStatus() != UserStatus.NOT_VERIFY) {
            throw new CustomException("user verified");
        }
        if (user.getRoleId() != USER_ROLE) {
            throw new CustomException("just use for verify user account");
        }

        EmailVerificationCode emailVerificationCode = emailVerificationCodeRepository.getByUserId(user.getId());
        if (emailVerificationCode == null) {
            emailVerificationCode = EmailVerificationCode
                    .builder()
                    .isUsed(false)
                    .expiresAt(LocalDateTime.now().plusMinutes(expireTime))
                    .verificationCode(code)
                    .userId(user.getId())
                    .build();
        }

        emailVerificationCode.setVerificationCode(code);
        emailVerificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(expireTime));
        emailVerificationCode.setUsed(false);

        emailVerificationCodeRepository.save(emailVerificationCode);

        sendEmailVerify(email, code, user.getFirstName() + " " + user.getLastName());

        return true;
    }

    @Override
    public boolean verifyEmail(String email, String code) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new CustomException("not found user with email " + email);
        }
        if (user.getStatus() != UserStatus.NOT_VERIFY) {
            throw new CustomException("user verified");
        }

        EmailVerificationCode emailVerificationCode = emailVerificationCodeRepository.getByVerificationCodeAndUserId(code, user.getId());
        if (emailVerificationCode == null) {
            throw new CustomException("invalid code");
        }

        if (LocalDateTime.now().isAfter(emailVerificationCode.getExpiresAt())) {
            throw new CustomException("code is expire");
        }

        if (emailVerificationCode.isUsed()) {
            throw new CustomException("code is used");
        }

        emailVerificationCode.setUsed(true);
        emailVerificationCodeRepository.save(emailVerificationCode);

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean forgetPassword(String email, String code) {
        return false;
    }

    @Override
    public boolean createForgetPassword(String email) {
        return false;
    }

    @Override
    public boolean createStaffAccount(CreateStaffRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("email is existed!");
        }

        User user = User
                .builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .roleId(STAFF_ROLE)
                .status(UserStatus.ACTIVE)
                .build();
        User save = userRepository.save(user);

        Staff build = Staff
                .builder()
                .userId(save.getId())
                .build();

        staffRepository.save(build);

        return true;
    }

    @Override
    public boolean updateStaffAccount(long id, UpdateStaffRequestDTO request) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("not found staff account with id " + id);
        }

        if (user.getRoleId() != STAFF_ROLE) {
            throw new CustomException("account is not staff");
        }

        if (request.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean updateStaffStatus(long id, int status) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("not found staff account with id " + id);
        }

        if (user.getRoleId() != STAFF_ROLE) {
            throw new CustomException("account is not staff");
        }
        if (user.getStatus() == status) {
            return true;
        }

        user.setStatus(status);
        userRepository.save(user);

        return true;
    }

    @Override
    public StaffResponseDTO getStaffAccountById(long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new CustomException("not found staff account with id " + id);
        }

        if (user.getRoleId() != STAFF_ROLE) {
            throw new CustomException("account is not staff");
        }

        return StaffResponseDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .address(user.getAddress())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public PageResponseDTO<StaffListResponseDTO> getStaffList(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<User> users = searchRepository.getEntitiesPage(User.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(users.map(UserMapper::staffDTO).toList(), users.getTotalPages());
    }

    @Override
    public List<StaffListResponseDTO> getAllStaffList() {
        List<User> userList = userRepository.findByRoleIdAndStatusNot(STAFF_ROLE, 0);
        return userList.stream().map(UserMapper::staffDTO).toList();
    }

    private void sendEmailApproveExpert(String email, String password, String fullName) {
        String subject = "Thông Báo Chấp Nhận Đơn Xin Trở Thành Chuyên Gia trên GotchaJob";
        String body = "Kính gửi " + fullName + ",\n" +
                "\n" +
                "Chúng tôi rất vui mừng thông báo rằng yêu cầu của bạn để trở thành chuyên gia trên GotchaJob đã được chấp nhận.\n" +
                "\n" +
                "Thông tin tài khoản của bạn trên GotchaJob:\n" +
                "\n" +
                "Tên đăng nhập: " + email + "\n" +
                "Mật khẩu: " + password + "\n" +
                "\n" +
                "Chúng tôi tin rằng bạn sẽ tuân thủ các giá trị và tiêu chuẩn của GotchaJob, đồng thời cung cấp những dịch vụ tư vấn chuyên nghiệp có giá trị cho những ai cần. Những đóng góp của bạn sẽ tạo ra tác động tích cực đối với cộng đồng.\n" +
                "\n" +
                "Cảm ơn bạn đã tin tưởng và tham gia cùng chúng tôi. Chúng tôi rất mong được hợp tác với bạn và tạo ra sự khác biệt trong cuộc sống của người khác.\n" +
                "\n" +
                "Trân trọng,\n" +
                "\n" +
                "Đội ngũ GotchaJob\n";



        emailService.sendEmail(email, subject, body);
    }

    private void sendEmailRejectExpert(String email, String fullName) {
        String subject = "Thông Báo Từ Chối Đơn Ứng Tuyển Chuyên Gia trên GotchaJob";
        String body = "Kính gửi " + fullName + ",\n" +
                "\n" +
                "Chúng tôi hy vọng bạn nhận được thông báo này trong tình trạng tốt.\n" +
                "\n" +
                "Chúng tôi viết thư này để thông báo rằng, rất tiếc, đơn ứng tuyển của bạn để trở thành chuyên gia trên GotchaJob đã bị từ chối. Mặc dù chúng tôi rất cảm kích về sự quan tâm của bạn, nhưng sau khi xem xét kỹ lưỡng, hội đồng xét duyệt đã quyết định không tiếp nhận ứng tuyển này.\n" +
                "\n" +
                "Dù không có cơ hội đóng góp với tư cách chuyên gia vào thời điểm này, chúng tôi khuyến khích bạn tiếp tục tìm kiếm những cơ hội khác để chia sẻ kiến thức và kỹ năng của mình. Chúng tôi rất đánh giá cao sự cam kết của bạn đối với sự phát triển nghề nghiệp của bản thân.\n" +
                "\n" +
                "Nếu bạn có bất kỳ câu hỏi nào hoặc cần thêm thông tin, vui lòng liên hệ với chúng tôi. Chúng tôi cũng rất hoan nghênh phản hồi hoặc ý kiến của bạn để cải thiện quy trình tuyển chọn trong tương lai.\n" +
                "\n" +
                "Cảm ơn bạn đã quan tâm đến GotchaJob và đã dành thời gian cho đơn ứng tuyển của mình. Chúc bạn những điều tốt đẹp nhất trong hành trình nghề nghiệp của mình.\n" +
                "\n" +
                "Trân trọng,\n" +
                "\n" +
                "Đội ngũ GotchaJob\n";



        emailService.sendEmail(email, subject, body);
    }

    private void sendEmailVerify(String email, String code, String fullName) {
        String subject = "Xác Minh Email của Bạn cho GotchaJob";
        String body = "Chào " + fullName + ",\n" +
                "\n" +
                "Cảm ơn bạn đã đăng ký với GotchaJob. Để hoàn tất quá trình đăng ký và xác minh email, vui lòng sử dụng mã xác minh sau:\n" +
                "\n" +
                "Mã xác minh của bạn: \n" + code +
                "\n" +
                "Mã này sẽ hết hạn trong 10 phút.\n" +
                "\n" +
                "Nếu bạn không tạo tài khoản với GotchaJob, vui lòng bỏ qua email này.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Đội ngũ GotchaJob\n";

        emailService.sendEmail(email, subject, body);
    }
}
