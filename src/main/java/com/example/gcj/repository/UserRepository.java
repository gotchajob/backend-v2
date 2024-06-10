package com.example.gcj.repository;

import com.example.gcj.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(long id);
    User getUserByEmail(String email);

    Page<User> getUserByStatusAndRoleId(int status, int roleId, Pageable pageable);
    boolean existsByEmail(String email);
}
