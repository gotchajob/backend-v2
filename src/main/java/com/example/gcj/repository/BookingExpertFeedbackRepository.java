package com.example.gcj.repository;

import com.example.gcj.model.BookingExpertFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingExpertFeedbackRepository extends JpaRepository<BookingExpertFeedback, Long> {
}
