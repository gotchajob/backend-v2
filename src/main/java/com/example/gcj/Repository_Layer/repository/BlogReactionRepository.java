package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BlogReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogReactionRepository extends JpaRepository<BlogReaction, Long> {
    BlogReaction findBlogReactionByBlogIdAndUserId(long blogId, long userId);
    @Query("SELECT AVG(br.rating) FROM BlogReaction br WHERE br.rating IS NOT NULL AND br.blogId = :blogId")
    Double findAverageRatingByBlogIdWhereRatingIsNotNull(@Param("blogId") long blogId);

    long countByBlogIdAndReactionIdNotNull(long blogId);
    long countByBlogIdAndRatingNotNull(long blogId);
}
