package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingReportSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingReportSuggestRepository extends JpaRepository<BookingReportSuggest, Long> {
    BookingReportSuggest findById(long id);
}
