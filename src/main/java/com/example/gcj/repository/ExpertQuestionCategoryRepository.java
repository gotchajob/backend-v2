package com.example.gcj.repository;

import com.example.gcj.model.ExpertQuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertQuestionCategoryRepository extends JpaRepository<ExpertQuestionCategory, Long> {
    ExpertQuestionCategory findById(long id);
    List<ExpertQuestionCategory> findByCreatedBy(long createBy);
}
