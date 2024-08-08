package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingReportRepository extends JpaRepository<BookingReport, Long> {
    BookingReport findById(long id);
}
