package com.example.gcj.repository;

import com.example.gcj.model.BlogReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogReactionRepository extends JpaRepository<BlogReaction, Long> {
    List<BlogReaction> findReactionByBlogId(long blogId);
    List<BlogReaction> findReactionByUserId(int userId);
    Optional<BlogReaction> findBlogReactionByBlogIdAndUserId(long blogId, long userId);
    boolean existsByBlogIdAndUserId(long blogId, long userId);

    @Query(value="SELECT AVG(br.rating) AS averageRating FROM blog_reaction br WHERE br.blog_id = :blogId", nativeQuery = true)
    Double findAverageRating(long blogId);

    @Query(value="SELECT COUNT(br.rating) as ratingQuantity FROM blog_reaction br WHERE br.blog_id = :blogId AND br.rating IS NOT NULL", nativeQuery = true)
    Integer findRatingQuantity(long blogId);
}
