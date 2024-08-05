package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvCategoryRepository extends JpaRepository<CvCategory, Long> {
    CvCategory findById(long id);
}
