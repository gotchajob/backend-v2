package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedback;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback.BookingCustomerFeedbackTotalRatingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCustomerFeedbackRepository extends JpaRepository<BookingCustomerFeedback, Long> {
    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.id = :id AND b.status != 0")
    BookingCustomerFeedback findById(long id);

    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.status != 0")
    List<BookingCustomerFeedback> findAll();
    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.bookingId =:bookingId AND b.status != 0")
    List<BookingCustomerFeedback> findByBookingId(long bookingId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.booking_customer_feedback.BookingCustomerFeedbackTotalRatingResponseDTO(bcf.rating, count(*)) " +
            "FROM BookingCustomerFeedback bcf INNER JOIN Booking b ON  bcf.bookingId = b.id " +
            "WHERE b.expertId =:expertId AND bcf.status = 1 AND b.status != 0" +
            "group by bcf.rating " +
            "order by bcf.rating")
    List<BookingCustomerFeedbackTotalRatingResponseDTO> getTotalRatingByExpert(long expertId);


    @Query("SELECT bcf " +
            "FROM BookingCustomerFeedback bcf INNER JOIN Booking b ON  bcf.bookingId = b.id " +
            "WHERE (:expertId is null OR b.expertId =:expertId) AND bcf.status = 1 AND b.status != 0")
    Page<BookingCustomerFeedback> findByExpertId(Long expertId, Pageable pageable);

    boolean existsByBookingIdAndStatus(long bookingId, int status);
}
