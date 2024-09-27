package com.example.gcj.Service_Layer.impl;

import com.example.gcj.External_Service.EmailService;
import com.example.gcj.Repository_Layer.model.*;
import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Service_Layer.dto.booking.*;
import com.example.gcj.Service_Layer.dto.cv.CvBookingResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionBookingResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserBookingInfoResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import com.example.gcj.Service_Layer.service.*;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.BookingMapper;
import com.example.gcj.Shared.util.status.AvailabilityStatus;
import com.example.gcj.Shared.util.status.BookingStatus;
import com.example.gcj.Shared.util.status.ExpertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final CvRepository cvRepository;
    private final ExpertRepository expertRepository;
    private final BookingSkillService bookingSkillService;
    private final AvailabilityRepository availabilityRepository;
    private final CustomerRepository customerRepository;
    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final PolicyService policyService;
    private final EmailService emailService;
    private final AccountService accountService;
    private final ExpertService expertService;

    @Override
    public void delete(long id) {
        updateStatus(id, BookingStatus.DELETE);
    }

    @Override
    public boolean update(long id, UpdateBookingRequestDTO request) {
        return false;
    }

    @Transactional
    @Override
    public boolean create(long customerId, CreateBookingRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        double cost = checkExpertAndGetCost(request.getExpertId());
        double currentBalance = accountService.getCurrentBalance();
        if (currentBalance < cost) {
            throw new CustomException("không đủ tiền trong tài khoản");
        }

        checkBookingDate(request.getAvailabilityId(), request.getExpertId());
        checkBookingCv(request.getCustomerCvId(), customerId);

        Booking build = Booking
                .builder()
                .customerId(customerId)
                .expertId(request.getExpertId())
                .availability(Availability.builder().id(request.getAvailabilityId()).build())
                .customerCvId(request.getCustomerCvId())
                .note(request.getNote())
                .status(BookingStatus.WAIT_EXPERT_ACCEPT)
                .oldBooking(null)
                .build();
        Booking save = bookingRepository.save(build);

        accountService.bookingPayment(cost, save.getId());
        if (request.getBookingSkill() == null || request.getBookingSkill().isEmpty()) {
            return true;
        }
        bookingSkillService.add(request.getBookingSkill(), save.getId());

        return true;
    }

    private double checkExpertAndGetCost(long expertId) {
        Expert expert = expertRepository.getById(expertId);
        if (expert == null) {
            throw new CustomException("không tìm thấy chuyên gia");
        }
        if (expert.getStatus() != ExpertStatus.BOOKING) {
            throw new CustomException("chuyên gia không nhận đặt lịch");
        }

        return expert.getCost();
    }

    private void checkBookingDate(long availabilityId, long expertId) {
        long minusToBooking = policyService.getByKey(PolicyKey.MINUS_TO_BOOKING, Long.class);
        Availability availability = availabilityRepository.findById(availabilityId);
        if (availability == null) {
            throw new CustomException("không tìm thấy lịch rảnh của chuyên gia ");
        }
        if (availability.getStatus() != AvailabilityStatus.VALID) {
            throw new CustomException("lịch rảnh của chuyên gia không hợp lệ");
        }

        if (availability.getExpertId() != expertId) {
            throw new CustomException("lịch rảnh không phải của chuyên gia đã chọn");
        }

        LocalDateTime interviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
        if (LocalDateTime.now().plusMinutes(minusToBooking).isAfter(interviewTime)) {
            throw new CustomException("cần đặt lich trước ngày, giờ phỏng vấn " + minusToBooking + " phút");
        }

        availability.setStatus(AvailabilityStatus.BOOKED);
        availabilityRepository.save(availability);
    }

    private void checkBookingCv(long cvId, long customerId) {
        Cv cv = cvRepository.getById(cvId);
        if (cv == null) {
            throw new CustomException("không tìm thấy cv");
        }

        if (cv.getCustomerId() != customerId) {
            throw new CustomException("cv đã chọn không phải của bạn");
        }
    }

    @Override
    public List<BookingListResponseDTO> getAll() {
        List<Booking> bookings = bookingRepository.findAll();

        long minusToCancelBooking = getMinusToCancel();
        List<BookingListResponseDTO> list = bookings.stream().map(b -> BookingMapper.toDto(b, minusToCancelBooking)).toList();
        addCustomerAndExpertInfo(list);
        return list;
    }

    @Override
    public List<BookingListResponseDTO> getByCurrent(long customerId) {
        List<Booking> bookings = bookingRepository.getByCustomerId(customerId);

        long minusToCancelBooking = getMinusToCancel();

        List<BookingListResponseDTO> list = bookings.stream().map(b -> BookingMapper.toDto(b, minusToCancelBooking)).toList();
        addCustomerAndExpertInfo(list);
        return list;

    }

    private void addCustomerAndExpertInfo(List<BookingListResponseDTO> list) {
        for (BookingListResponseDTO b : list) {
            UserBookingInfoResponseDTO customerInfo = customerRepository.customerInfo(b.getCustomerId());
            UserInfoResponseDTO expertInfo = expertRepository.getExpertInfo(b.getExpertId());

            b.setCustomerInfo(customerInfo);
            b.setExpertInfo(expertInfo);

        }
    }

    @Override
    public List<BookingListResponseDTO> getByCurrentAndStatus(long customerId, Integer status) {
        List<Booking> bookings = status == null ? bookingRepository.getByCustomerId(customerId)
                : bookingRepository.getByCustomerIdAndStatus(customerId, status);

        long minusToCancelBooking = getMinusToCancel();
        List<BookingListResponseDTO> list = bookings.stream().map(b -> BookingMapper.toDto(b, minusToCancelBooking)).toList();
        addCustomerAndExpertInfo(list);

        return list;
    }

    @Override
    public BookingResponseDTO getById(long id) {
        Booking booking = get(id);
        UserBookingInfoResponseDTO customerInfo = customerRepository.customerInfo(booking.getCustomerId());
        if (customerInfo == null) {
            throw new CustomException("không tìm thấy thông tin khách hàng");
        }

        Cv customerCv = cvRepository.getById(booking.getCustomerCvId());
        if (customerCv == null) {
            throw new CustomException("không tìm thấy cv của khách hàng");
        }

        UserInfoResponseDTO expertInfo = expertRepository.getExpertInfo(booking.getExpertId());

        CvBookingResponseDTO customerCvResponse = CvBookingResponseDTO
                .builder()
                .id(customerCv.getId())
                .image(customerCv.getImage())
                .name(customerCv.getName())
                .build();

        Availability availability = booking.getAvailability();
        List<Long> expertSkillOptionIds = booking.getBookingSkills().stream().map(BookingSkill::getExpertSkillOptionId).toList();
        List<SkillOptionBookingResponseDTO> skillOptionBooking = expertSkillOptionRepository.getByExpertSkillOptionId(expertSkillOptionIds);

        long minusToCancel = policyService.getByKey(PolicyKey.MINUS_TO_CANCEL_BOOKING, Long.class);
        boolean canCancel = (booking.getStatus() == 1 || booking.getStatus() == 2)
                && (LocalDateTime.now().plusMinutes(minusToCancel).isBefore(availability.getAvailableDate().atTime(availability.getStartTime())));

        return BookingResponseDTO
                .builder()
                .id(booking.getId())
                .expertId(booking.getExpertId())
                .customerId(booking.getCustomerId())
                .startInterviewDate(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endInterviewDate(availability.getAvailableDate().atTime(availability.getEndTime()))
                .note(booking.getNote())
                .rejectReason(booking.getRejectReason())
                .customerInfo(customerInfo)
                .expertInfo(expertInfo)
                .customerCv(customerCvResponse)
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .canCancel(canCancel)
                .skillOptionBooking(skillOptionBooking)
                .build();
    }

    @Override
    public boolean updateStatus(long id, int status) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("không tìm thấy lich hẹn");
        }

        booking.setStatus(status);
        bookingRepository.save(booking);

        return true;
    }

    @Override
    public boolean approve(long expertId, long id) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("lịch hẹn không phải của bạn");
        }

        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT) {
            throw new CustomException("trạng thái của lịch hẹn không hợp lệ");
        }
        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("không tìm thấy lich rảnh");
        }

        long minusBeforeToApprove = policyService.getByKey(PolicyKey.MINUS_BEFORE_APPROVE_BOOKING, Long.class);
        LocalDateTime interviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
        if (LocalDateTime.now().plusMinutes(minusBeforeToApprove).isAfter(interviewTime)) {
            throw new CustomException("cần phải phê duyện trước ngày, giờ phỏng vấn " + minusBeforeToApprove + " phút");
        }

        booking.setStatus(BookingStatus.WAIT_TO_INTERVIEW);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean reject(long expertId, long id, RejectBookingRequestDTO request) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("lịch hẹn không phải của bạn");
        }

        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT) {
            throw new CustomException("trạng thái của lịch hẹn không hợp lệ");
        }

        booking.setStatus(BookingStatus.CANCEL_BY_EXPERT);
        booking.setRejectReason(request.getReason());
        bookingRepository.save(booking);

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("không tìm thấy lich rảnh");
        }

        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);

        accountService.refundWhenCancelBooking(booking.getCustomerId(), booking.getId());

        int expertPointWhenRejectBooking = policyService.getByKey(PolicyKey.EXPERT_POINT_WHEN_REJECT_BOOKING, Integer.class);
        expertService.updateExpertPoint(expertId, -expertPointWhenRejectBooking);

        return true;
    }

    @Override
    public List<BookingListResponseDTO> getByExpertId(long expertId) {
        List<Booking> bookings = bookingRepository.getByExpertId(expertId);
        long minusToCancelBooking = getMinusToCancel();
        List<BookingListResponseDTO> list = bookings.stream().map(b -> BookingMapper.toDto(b, minusToCancelBooking)).toList();
        addCustomerAndExpertInfo(list);
        return  list;
    }

    @Override
    public List<BookingListResponseDTO> getByExpertIdAndStatus(long expertId, Integer status) {
        List<Booking> bookings = status == null
                ? bookingRepository.getByExpertId(expertId)
                : bookingRepository.getByExpertIdAndStatus(expertId, status);
        long minusToCancelBooking = getMinusToCancel();
        List<BookingListResponseDTO> list = bookings.stream().map(b -> BookingMapper.toDto(b, minusToCancelBooking)).toList();
        addCustomerAndExpertInfo(list);
        return  list;
    }

    @Override
    public boolean endBooking(long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("không tìm thấy lich hẹn");
        }

        booking.setStatus(BookingStatus.WAIT_TO_FEEDBACK);
        bookingRepository.save(booking);

        return true;
    }

    @Override
    public boolean completeBooking(long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("not found booking with id " + id);
        }

        booking.setStatus(BookingStatus.COMPLETE);
        bookingRepository.save(booking);

        return true;
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void autoCompleteBooking() {
        List<Booking> bookingList = bookingRepository.findByStatusAndExpireCompleteDateBefore(BookingStatus.WAIT_TO_FEEDBACK, LocalDateTime.now());
        int pointWhenCompleteBooking = policyService.getByKey(PolicyKey.EXPERT_POINT_WHEN_COMPLETE_BOOKING, Integer.class);

        for (Booking booking : bookingList) {
            booking.setStatus(BookingStatus.COMPLETE);
            Long userId = expertRepository.getUserIdById(booking.getExpertId());
            if (userId == null) {
                continue;
            }

            expertService.updateExpertPoint(booking.getExpertId(), pointWhenCompleteBooking);
            boolean result = accountService.sendMoneyToExpert(userId, booking.getId());
            if (!result) {
                continue;
            }

            bookingRepository.save(booking);
        }
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void autoRejectBooking() {
        long minusToApproveBooking = policyService.getByKey(PolicyKey.MINUS_BEFORE_APPROVE_BOOKING, Long.class);
        LocalDateTime current = LocalDateTime.now();
        current.plusMinutes(minusToApproveBooking);
        List<Booking> bookingToReject = bookingRepository.findBookingToReject(BookingStatus.WAIT_EXPERT_ACCEPT, current.toLocalDate(), current.toLocalTime());

        for (Booking booking : bookingToReject) {
            try {
                reject(booking.getExpertId(), booking.getId(), RejectBookingRequestDTO.builder().reason("quá hạn duyệt lịch hẹn").build());
            } catch (Exception e) {

            }
        }
    }

    @Override
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    public void updateToBookingInterviewing() {
        long minusToSendNotifyInterview = policyService.getByKey(PolicyKey.MINUS_TO_SEND_NOTIFY_INTERVIEWING, Long.class);
        LocalDateTime now = LocalDateTime.now().plusMinutes(minusToSendNotifyInterview);

        List<Booking> bookingList = bookingRepository.findByStatus(BookingStatus.WAIT_TO_INTERVIEW);
        for (Booking booking : bookingList) {
            Availability availability = booking.getAvailability();
            LocalDateTime startInterviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
            LocalDateTime endInterviewTime = availability.getAvailableDate().atTime(availability.getEndTime());

            if (now.isAfter(startInterviewTime)) {
                booking.setStatus(BookingStatus.INTERVIEWING);
                bookingRepository.save(booking);
            }
            sendEmailToNotifyBooking(booking);
        }
    }

    @Override
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    public void updateToEndBooking() {
        long minusToAutoEndBooking = policyService.getByKey(PolicyKey.MINUS_TO_AUTO_END_BOOKING, Long.class);

        LocalDateTime now = LocalDateTime.now().minusMinutes(minusToAutoEndBooking);

        List<Booking> bookingList = bookingRepository.findByStatus(BookingStatus.INTERVIEWING);
        for (Booking booking : bookingList) {
            Availability availability = booking.getAvailability();
            LocalDateTime startInterviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
            LocalDateTime endInterviewTime = availability.getAvailableDate().atTime(availability.getEndTime());

            if (now.isAfter(endInterviewTime)) {
                long minusToAutoCompleteBooking = policyService.getByKey(PolicyKey.MINUS_TO_AUTO_COMPLETE_BOOKING, Long.class);
                booking.setStatus(BookingStatus.WAIT_TO_FEEDBACK);
                booking.setExpireCompleteDate(LocalDateTime.now().plusMinutes(minusToAutoCompleteBooking));
                bookingRepository.save(booking);

                sendEmailToCustomerWhenEndBooking(booking.getCustomerId(), booking.getId());
            }
        }
    }

    private void sendEmailToCustomerWhenEndBooking(long customerId, long bookingId) {
        UserBookingInfoResponseDTO customerInfo = customerRepository.customerInfo(customerId);
        if (customerInfo == null || customerInfo.getEmail() == null) {
            return;
        }
        String linkFeedback = "https://www.gotchajob.vn/feedback/" + bookingId;

        String subject = "[Gotcha Job] Please Share Your Feedback on the Recent Booking";
        String body = "Dear " + customerInfo.getFullName() + ",\n\n" +
                "Thank you for completing your booking with us! We hope you had a great experience.\n\n" +
                "We value your feedback and would appreciate it if you could take a moment to share your thoughts about the expert you booked. " +
                "Please click the link below to provide your feedback:\n\n" +
                linkFeedback + "\n\n" +
                "Your feedback helps us improve our service and ensure a better experience for all our users.\n\n" +
                "Best regards,\n" +
                "Gotcha Job Team";

        emailService.sendEmail(customerInfo.getEmail(), subject, body);
    }

    @Override
    public boolean cancel(long customerId, long id) {
        Booking booking = get(id);
        if (booking.getCustomerId() != customerId) {
            throw new CustomException("buổi hẹn không phải của bạn");
        }

        LocalDateTime now = LocalDateTime.now();
        long numberCancelThisMonth = bookingRepository.countByCustomerIdAndStatusAndUpdatedAtMonthAndUpdatedAtYear(customerId, BookingStatus.CANCEl_BY_CUSTOMER, now.getMonthValue(), now.getYear() );
        long numberCanCancelByCustomer = policyService.getByKey(PolicyKey.NUMBER_CANCEL_BY_CUSTOMER, Long.class);
        if (numberCancelThisMonth >= numberCanCancelByCustomer) {
            throw new CustomException("chỉ có thể hủy tối đa " + numberCanCancelByCustomer + " lần mỗi tháng!");
        } 

        Availability availability = checkBookingToCancelAndGetAvailability(booking);

        LocalDateTime interviewDay = availability.getAvailableDate().atTime(availability.getStartTime());
        if (!canCancelBooking(interviewDay)) {
            throw new CustomException("quá trễ để hủy buổi hẹn");
        }


        availability.setStatus(AvailabilityStatus.VALID);
        availabilityRepository.save(availability);

        booking.setStatus(BookingStatus.CANCEl_BY_CUSTOMER);
        bookingRepository.save(booking);

        accountService.refundWhenCancelBooking(booking.getCustomerId(), booking.getId());
        return true;
    }

    @Override
    @Transactional
    public boolean cancelByExpert(long expertId, long id) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("current customer not same with customer in booking");
        }



        if (booking.getStatus() != BookingStatus.WAIT_TO_INTERVIEW) {
            throw new CustomException("current status is not wait to interview");
        }

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("not found availability");
        }

        LocalDateTime interviewDay = getInterviewDay(availability.getAvailableDate(), availability.getStartTime());
        if (!canCancelBooking(interviewDay)) {
            throw new CustomException("too late to cancel");
        }
        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);

        booking.setStatus(BookingStatus.CANCEL_BY_EXPERT);
        bookingRepository.save(booking);

        accountService.refundWhenCancelBooking(booking.getCustomerId(), booking.getId());

        LocalDateTime now = LocalDateTime.now();
        long numberCancelThisMonth = bookingRepository.countByExpertIdAndStatusAndUpdatedAtMonthAndUpdatedAtYear(expertId, BookingStatus.CANCEl_BY_CUSTOMER, now.getMonthValue(), now.getYear() );
        long numberCanCancelByExpert = policyService.getByKey(PolicyKey.NUMBER_CANCEL_BY_EXPERT, Long.class);
        if (numberCancelThisMonth > numberCanCancelByExpert) {
            int pointWhenCancelBooking = policyService.getByKey(PolicyKey.EXPERT_POINT_WHEN_CANCEL_BOOKING, Integer.class);
            expertService.updateExpertPoint(expertId, -pointWhenCancelBooking);
        }

        return true;
    }

    private Availability checkBookingToCancelAndGetAvailability(Booking booking) {
        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT && booking.getStatus() != BookingStatus.WAIT_TO_INTERVIEW) {
            throw new CustomException("current status is not wait expert accept or wait to interview");
        }

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("not found availability");
        }

        return availability;
    }

    private LocalDateTime getInterviewDay(LocalDate date, LocalTime time) {
        return date.atTime(time);
    }

    private Booking get(long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("không tìm thấy buổi hẹn");
        }

        return booking;
    }

    private long getMinusToCancel() {
        return policyService.getByKey(PolicyKey.MINUS_TO_CANCEL_BOOKING, Long.class);
    }

    private boolean canCancelBooking(LocalDateTime startDateTime) {
        long minusCancelBooking = getMinusToCancel();
        return LocalDateTime.now().plusMinutes(minusCancelBooking).isBefore(startDateTime);
    }

    private void sendEmailToNotifyBooking(Booking booking) {
        if (booking == null || booking.getStatus() != BookingStatus.INTERVIEWING) {
            return;
        }

        UserBookingInfoResponseDTO customerInfo = customerRepository.customerInfo(booking.getCustomerId());
        if (customerInfo == null || customerInfo.getEmail() == null) {
            return;
        }
        String expertEmail = expertRepository.getEmailById(booking.getExpertId());
        if (expertEmail == null) {
            return;
        }

        String ggMeetLink = "https://meet.google.com/abc-defg-hij";

        String subject = "[Gotcha Job] Upcoming Interview Notification";
        String body = "Dear " + customerInfo.getFullName() + ",\n\n" +
                "We would like to remind you about your upcoming interview session. Please find the details below:\n\n" +
                "Interview Link (Google Meet): " + ggMeetLink + "\n" +
                "Booking ID: " + booking.getId() + "\n\n" +
                "We wish you the best of luck during the interview!\n\n" +
                "Best regards,\n" +
                "Gotcha Job Team";

        emailService.sendEmail(expertEmail, subject, body);
        emailService.sendEmail(customerInfo.getEmail(), subject, body);
    }
}
