package com.example.gcj.repository;

import com.example.gcj.model.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
    Page<BlogComment> getByBlogIdAndParentCommentId(long blogId, Long parentCommentId, Pageable pageable);
}
