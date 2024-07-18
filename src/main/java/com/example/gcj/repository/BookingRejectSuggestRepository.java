package com.example.gcj.repository;

import com.example.gcj.model.BookingRejectSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRejectSuggestRepository extends JpaRepository<BookingRejectSuggest, Long> {
    BookingRejectSuggest findById(long id);

    List<BookingRejectSuggest> findByType(int type);
}
