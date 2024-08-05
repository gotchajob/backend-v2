package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy getByKey(String key);
    Policy findById(long id);

}
