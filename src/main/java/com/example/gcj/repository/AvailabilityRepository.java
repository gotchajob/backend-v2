package com.example.gcj.repository;

import com.example.gcj.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT a FROM Availability a WHERE a.id =:id AND a.status != 0")
    Availability findById(long id);

    @Query("SELECT a FROM Availability a WHERE a.status != 0")
    List<Availability> findAll();
    List<Availability> getByExpertIdAndStatus(long expertId, int status);
}
