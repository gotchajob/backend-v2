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

    List<User> getUserByStatusAndRoleId(int status, int roleId, Pageable pageable);
    long countByStatusAndRoleId(int status, int roleId);

    Page<User> getAllByRoleId(int roleId, Pageable pageable);
}
