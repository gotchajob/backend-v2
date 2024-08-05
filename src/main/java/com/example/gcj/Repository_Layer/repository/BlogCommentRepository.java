package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
    Page<BlogComment> getByBlogIdAndParentCommentId(long blogId, Long parentCommentId, Pageable pageable);
    long countByBlogIdAndParentCommentId(long blogId, Long parentCommentId);
    BlogComment getById(long id);
}
