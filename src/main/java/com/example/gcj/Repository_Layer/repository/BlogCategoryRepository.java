package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
    @Query("SELECT bc FROM BlogCategory bc WHERE bc.status != 0 AND bc.id =:id ")
    BlogCategory findById(long id);

    @Query("SELECT bc FROM BlogCategory bc WHERE bc.status != 0 ")
    List<BlogCategory> findAll();
}
