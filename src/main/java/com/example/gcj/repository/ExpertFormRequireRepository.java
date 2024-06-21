package com.example.gcj.repository;

import com.example.gcj.model.ExpertFormRequire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertFormRequireRepository extends JpaRepository<ExpertFormRequire, Long> {
    ExpertFormRequireRepository findById(long id);

    @Query("SELECT e FROM ExpertFormRequire e WHERE e.categoryId =:categoryId AND e.status != 0")
    List<ExpertFormRequire> findByCategoryId(Long categoryId);
    List<ExpertFormRequire> findByCategoryIdInAndStatus(List<Long> categoryIds, int status);

}
