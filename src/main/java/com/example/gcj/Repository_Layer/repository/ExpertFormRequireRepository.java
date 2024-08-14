package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertFormRequire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertFormRequireRepository extends JpaRepository<ExpertFormRequire, Long> {
    @Query("SELECT e FROM ExpertFormRequire e WHERE e.status != 0 AND e.id =:id")
    ExpertFormRequire findById(long id);

    @Query("SELECT e FROM ExpertFormRequire e WHERE e.categoryId =:categoryId AND e.status != 0")
    List<ExpertFormRequire> findByCategoryId(Long categoryId);

    @Query("SELECT e FROM ExpertFormRequire e WHERE (:categoryIds IS NULL OR e.categoryId in (:categoryIds)) AND e.status = 1")
    List<ExpertFormRequire> findByCategoryIdInAndStatus(List<Long> categoryIds);

}
