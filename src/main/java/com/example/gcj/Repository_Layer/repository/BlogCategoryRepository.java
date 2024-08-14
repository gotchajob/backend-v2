package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
    BlogCategory findById(long id);
}
