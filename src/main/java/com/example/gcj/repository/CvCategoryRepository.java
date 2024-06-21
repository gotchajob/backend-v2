package com.example.gcj.repository;

import com.example.gcj.model.CvCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvCategoryRepository extends JpaRepository<CvCategory, Long> {
    CvCategory findById(long id);
}
