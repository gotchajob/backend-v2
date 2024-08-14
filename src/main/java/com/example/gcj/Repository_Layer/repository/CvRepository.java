package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    @Query("SELECT c FROM Cv c WHERE c.id = :id and c.status != 0")
    Cv getById(long id);

    @Query("SELECT c FROM Cv c WHERE c.customerId = :customerId and c.status != 0")
    List<Cv> findByCustomerId(@Param("customerId") long customerId);

}
