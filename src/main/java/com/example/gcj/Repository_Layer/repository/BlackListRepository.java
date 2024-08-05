package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
