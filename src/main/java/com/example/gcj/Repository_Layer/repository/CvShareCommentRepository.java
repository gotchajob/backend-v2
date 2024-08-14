package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvShareCommentRepository extends JpaRepository<CvShareComment, Long> {
    CvShareComment findById(long id);
}
