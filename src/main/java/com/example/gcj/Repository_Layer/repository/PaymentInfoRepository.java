package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    PaymentInfo findById(long id);
}
