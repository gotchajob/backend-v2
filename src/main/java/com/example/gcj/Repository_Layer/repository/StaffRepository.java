package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Staff;
import com.example.gcj.Service_Layer.dto.user.UserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    boolean existsByUserId(long userId);

    @Query("SELECT s.id FROM Staff s INNER JOIN User u ON s.userId = u.id WHERE u.email =:email ")
    Long currentStaffId(String email);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.user.UserProfileDTO(u.id, u.avatar, u.email, CONCAT(u.firstName, ' ' ,u.lastName), u.firstName, u.lastName, u.phone, u.address, u.roleId) " +
            "FROM Staff s JOIN User u ON s.userId = u.id " +
            "WHERE s.id =:expertId ")
    UserProfileDTO getStaffProfile(long expertId);
}
