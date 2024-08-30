package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvShareCommentRepository extends JpaRepository<CvShareComment, Long> {
    CvShareComment findById(long id);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO(csc.rating, count(csc.id)) " +
            "FROM CvShareComment csc " +
            "WHERE csc.cvShareId =:cvShareId AND csc.status = 1 " +
            "GROUP BY csc.rating")
    List<CvShareRatingListResponseDTO> getRatingByCvShareId(long cvShareId);
}
