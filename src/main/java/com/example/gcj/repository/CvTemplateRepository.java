package com.example.gcj.repository;

import com.example.gcj.model.CvTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvTemplateRepository extends JpaRepository<CvTemplate, Long> {
    CvTemplate findById(long id);

    @Query("SELECT c FROM CvTemplate c WHERE c.categoryId = :categoryId and c.status != 0")
    List<CvTemplate>  findByCategoryId(@Param("categoryId") long categoryId);

    @Query("SELECT c FROM CvTemplate c WHERE c.status != 0")
    List<CvTemplate>  findAll();
}
