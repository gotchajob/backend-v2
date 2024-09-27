package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingReportSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingReportSuggestRepository extends JpaRepository<BookingReportSuggest, Long> {
    BookingReportSuggest findById(long id);

    boolean existsByReportIdAndSuggestId(long reportId, long suggestId);
    @Query("SELECT brs FROM BookingReportSuggest brs WHERE (:reportId IS NULL OR brs.reportId =:reportId) ")
    List<BookingReportSuggest> findByReportId(Long reportId);

}
