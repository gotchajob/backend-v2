package com.example.gcj.repository;

import com.example.gcj.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    Cv findById(long id);

    @Query("SELECT c FROM Cv c WHERE c.userId = :userId and c.status != 0")
    List<Cv> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cv c WHERE c.status != 0 and c.userId is null")
    List<Cv> getTemplate();
}
