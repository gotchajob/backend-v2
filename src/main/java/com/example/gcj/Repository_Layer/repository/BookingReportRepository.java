package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingReportRepository extends JpaRepository<BookingReport, Long> {

    @Query("SELECT br FROM BookingReport br WHERE br.status != 0 AND br.id =:id")
    BookingReport findById(long id);
    @Query("SELECT br FROM BookingReport br WHERE br.status != 0")
    List<BookingReport> findAll();
    @Query("SELECT br FROM BookingReport br INNER JOIN Booking b ON br.bookingId = b.id WHERE br.status =:status AND b.expertId =:expertId ")
    Page<BookingReport> findByExpert(long expertId, int status, Pageable pageable);


    @Query("SELECT br FROM BookingReport br INNER JOIN Booking b ON br.bookingId = b.id WHERE br.status != 0 AND b.customerId =:customerId")
    Page<BookingReport> findByCustomer(long customerId, Pageable pageable);

    boolean existsByBookingIdAndStatus(long bookingId, int status);
}
