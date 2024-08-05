package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Reaction getById(long id);
}
