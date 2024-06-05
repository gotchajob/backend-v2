package com.example.gcj.repository;

import com.example.gcj.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    List<CommentReaction> findAll();
    List<CommentReaction> findReactionByCommentId(long commentId);
    CommentReaction findByUserIdAndCommentId(long userId, long commentId);
    long countByCommentIdAndReactionIdNotNull(long commentId);
    boolean existsByCommentIdAndUserIdAndReactionIdNotNull(long commentId, long userId);
}
