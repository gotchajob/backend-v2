package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    List<CommentReaction> findAll();
    List<CommentReaction> findReactionByCommentId(long commentId);
    CommentReaction findByUserIdAndCommentId(long userId, long commentId);
    long countByCommentIdAndReactionIdNotNull(long commentId);
    boolean existsByCommentIdAndUserIdAndReactionIdNotNull(long commentId, long userId);
}
