package com.example.gcj.repository;

import com.example.gcj.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT a FROM Availability a WHERE a.id =:id AND a.status != 0")
    Availability findById(long id);

    @Query("SELECT a FROM Availability a WHERE a.status != 0")
    List<Availability> findAll();

    @Query("SELECT a FROM Availability a WHERE a.status != 0 AND a.expertId = :expertId")
    List<Availability> getByExpertId(long expertId);

    @Query("SELECT a FROM Availability a WHERE a.expertId = :expertId AND a.status = 1 AND (a.availableDate > :validDate OR (a.availableDate = :validDate AND a.endTime > :currentTime))")
    List<Availability> getValidDate(long expertId,LocalDate validDate, LocalTime currentTime);

    @Query("SELECT COUNT(a) > 0 FROM Availability a WHERE a.status != 0 AND a.expertId = :expertId AND a.availableDate = :availableDate AND " +
            "(:startTime < a.endTime AND :endTime > a.startTime)")
    boolean isOverlappingAvailabilities(@Param("expertId") long expertId,
                                                     @Param("availableDate") LocalDate availableDate,
                                                     @Param("startTime") LocalTime startTime,
                                                     @Param("endTime") LocalTime endTime);
}
