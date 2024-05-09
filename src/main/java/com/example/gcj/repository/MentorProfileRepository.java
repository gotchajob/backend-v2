package com.example.gcj.repository;

import com.example.gcj.model.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {
}
