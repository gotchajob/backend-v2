package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CvShareRepository extends JpaRepository<CvShare, Long> {
    @Query("SELECT cs FROM CvShare cs WHERE cs.id =:id AND cs.status != 0")
    CvShare findById(long id);
}
