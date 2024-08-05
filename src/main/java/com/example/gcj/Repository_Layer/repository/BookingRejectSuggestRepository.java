package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingRejectSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRejectSuggestRepository extends JpaRepository<BookingRejectSuggest, Long> {
    BookingRejectSuggest findById(long id);

    List<BookingRejectSuggest> findByType(int type);
}
