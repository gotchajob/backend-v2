package com.example.gcj.repository;

import com.example.gcj.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    List<CommentReaction> findAll();
    List<CommentReaction> findReactionByCommentId(long commentId);

    long countByCommentId(long commentId);
    boolean existsByCommentIdAndUserId(long commentId, long userId);
}
