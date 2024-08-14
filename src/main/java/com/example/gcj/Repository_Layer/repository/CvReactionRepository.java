package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvReactionRepository extends JpaRepository<CvReaction, Long> {
    CvReaction findById(long id);
}
