package com.example.gcj.repository;

import com.example.gcj.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.id =:id")
    Booking findById(long id);

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.customerId =:customerId")
    List<Booking> getByCustomerId(long customerId);
    List<Booking> getByCustomerIdAndStatus(long customerId, int status);

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.expertId =:expertId")
    List<Booking> getByExpertId(long expertId);
    List<Booking> getByExpertIdAndStatus(long expertId, int status);
    List<Booking> findByStatus(int status);
    List<Booking> findByStatusAndExpireCompleteDateBefore(int status, LocalDateTime now);




}
