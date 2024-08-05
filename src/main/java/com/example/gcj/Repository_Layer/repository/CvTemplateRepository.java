package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvTemplateRepository extends JpaRepository<CvTemplate, Long> {

    @Query("SELECT c FROM CvTemplate c WHERE c.id = :id AND c.status != 0")
    CvTemplate findById(long id);

    @Query("SELECT c FROM CvTemplate c WHERE c.categoryId = :categoryId and c.status != 0")
    List<CvTemplate>  findByCategoryId(@Param("categoryId") long categoryId);

    @Query("SELECT c FROM CvTemplate c WHERE c.status != 0")
    List<CvTemplate>  findAll();
}
