package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvCategoryRepository extends JpaRepository<CvCategory, Long> {
    @Query("SELECT cc FROM CvCategory cc WHERE cc.status != 0 AND cc.id =:id ")
    CvCategory findById(long id);
    @Query("SELECT cc FROM CvCategory cc WHERE cc.status != 0 ")
    List<CvCategory> findAll();
}
