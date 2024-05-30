package com.example.gcj.repository;

import com.example.gcj.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> getAllByStatus(int status, Pageable pageable);
    Blog getByIdAndStatus(long id, int status);

    @Query(value = "SELECT * FROM blog b WHERE b.category_id = :categoryId AND b.id != :excludeBlogId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Blog> findRelateBlogs(long categoryId, long excludeBlogId, int limit);

    @Query(value = "SELECT * FROM blog b WHERE b.category_id = :categoryId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Blog> findBlogByCategoryId(long categoryId, int limit);
}
