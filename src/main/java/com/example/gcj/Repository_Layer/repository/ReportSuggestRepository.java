package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ReportSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportSuggestRepository extends JpaRepository<ReportSuggest, Long> {
    @Query("SELECT r FROM ReportSuggest r WHERE r.id =:id AND r.status != 0")
    ReportSuggest findById(long id);

    @Query("SELECT r FROM ReportSuggest r WHERE r.status != 0")
    List<ReportSuggest> findAll();
}
