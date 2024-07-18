package com.example.gcj.repository;

import com.example.gcj.model.BookingCustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCustomerFeedbackRepository extends JpaRepository<BookingCustomerFeedback, Long> {
    BookingCustomerFeedback findById(long id);
}
