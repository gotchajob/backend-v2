package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> getAllByStatus(int status, Pageable pageable);
    @Query("SELECT b FROM Blog b WHERE b.status != 0 AND (:categoryId IS NULL OR b.category.id =:categoryId) ")
    Page<Blog> findByCategory(Long categoryId, Pageable pageable);
    Page<Blog> getAllByCategoryIdAndStatus(long categoryId, int status, Pageable pageable);
    Blog getByIdAndStatus(long id, int status);
    @Query(value = "SELECT * FROM blog b WHERE b.category_id = :categoryId AND b.id != :excludeBlogId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Blog> findRelateBlogs(long categoryId, long excludeBlogId, int limit);

    @Query("SELECT b FROM Blog b WHERE b.id =:id AND b.status = 1")
    Blog getById(int id);

}
