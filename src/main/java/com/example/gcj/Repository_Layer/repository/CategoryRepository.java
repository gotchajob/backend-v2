package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id =:id AND c.status != 0")
    Category findById(long id);

    @Query("SELECT c FROM Category c WHERE c.status != 0")
    List<Category> findAll();
}
