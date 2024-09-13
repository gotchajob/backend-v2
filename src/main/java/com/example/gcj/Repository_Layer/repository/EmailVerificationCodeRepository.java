package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {
    EmailVerificationCode getByVerificationCodeAndUserId(String code, long userId);
    EmailVerificationCode getByUserId(long userId);
}
