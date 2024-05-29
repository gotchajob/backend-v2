package com.example.gcj.repository;

import com.example.gcj.model.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
    Page<Faq> findByStatus(int status, Pageable pageable);
    Faq findById(long id);
}
