package com.example.gcj.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    final static String DEV_EMAIL = "gotchajob.dev@gmail.com";

    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(DEV_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEmailResetPassword(String email, String code, String fullName) {
        String subject = " Password Reset Request for GotchaJob.com";
        String body = "Dear "+ fullName +",\n" +
                "\n" +
                "I hope this email finds you well. It appears that you have requested a password reset for your account. To ensure the security of your account, we have initiated the password reset process.\n" +
                "\n" +
                "Please use the following code to reset your password:\n" +
                "\n" +
                "Reset Code: " + code + "\n" +
                "\n" +
                "If you did not initiate this password reset request, please disregard this email. Your account security is important to us, and we recommend changing your password periodically to enhance security.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "GotchaJob\n";

        sendEmail(email, subject, body);
    }

    public void sendEmailGetLinkFormMentor(String email, String url) {
        String subject = "Link form to register mentor of GotchaJob.com";
        String body = "Dear " + email + ",\n" +
                "\n" +
                "click link to get form register mentor: \n" +
                url + "\n" +
                "Best regards,\n" +
                "GotchaJob";

        sendEmail(email, subject, body);
    }


    public void updateExpertRegisForm(String email,String note, String updateUrl, int status) {
        String subject = "";
        String body = "";
            if(status == 1) {
                subject = "Chúc mừng! Form cập nhật thông tin của bạn đã được chấp nhận";
                body = "Kính gửi " + email + ",\n\n" +
                        "Chúc mừng! Form cập nhật thông tin của bạn đã được chấp nhận. " +
                        "Thông tin của bạn sẽ được cập nhật trong hệ thống của chúng tôi.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ hỗ trợ";
            }
        else if(status == 2) {
            subject = "Yêu cầu chỉnh sửa form cập nhật thông tin";
            body = "Kính gửi " + email + ",\n\n" +
                    "Form cập nhật thông tin của bạn cần được chỉnh sửa.\n" +
                    "Lý do: " + note + "\n\n" +
                    "Vui lòng truy cập link sau để chỉnh sửa form của bạn: " + updateUrl + "\n\n" +
                    "Trân trọng,\n" +
                    "Đội ngũ hỗ trợ";
        }
        sendEmail(email, subject, body);
    }
}
