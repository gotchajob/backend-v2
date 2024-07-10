package com.example.gcj.repository;

import com.example.gcj.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.id =:id")
    Booking findById(long id);

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.customerId =:customerId")
    List<Booking> getByCustomerId(long customerId);
}
