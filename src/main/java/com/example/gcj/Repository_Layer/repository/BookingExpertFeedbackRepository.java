package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackRepository extends JpaRepository<BookingExpertFeedback, Long> {
    @Query("SELECT b FROM BookingExpertFeedback b WHERE b.bookingId = :bookingId AND b.status != 0")
    List<BookingExpertFeedback> getByBookingId(long bookingId);

    @Query("SELECT b FROM BookingExpertFeedback b WHERE b.status != 0")
    List<BookingExpertFeedback> findAll();


    @Query("SELECT b FROM BookingExpertFeedback b WHERE b.id = :id AND b.status != 0")
    BookingExpertFeedback findById(long id);
}
