package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Service_Layer.dto.user.UserBookingInfoResponseDTO;
import com.example.gcj.Repository_Layer.model.Customer;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.id FROM Customer c WHERE c.userId = :userId")
    Long getIdByUserId(@Param("userId") long userId);

    @Query("SELECT c FROM Customer c INNER JOIN User u ON c.userId = u.id WHERE u.email =:email")
    Customer currentCustomer(String email);

    Customer findById(long id);
    Customer findByUserId(long userId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.user.UserBookingInfoResponseDTO(u.firstName, u.lastName, u.email, u.avatar)" +
            "FROM Customer c INNER JOIN User u ON c.userId = u.id " +
            "WHERE c.id =:customerId")
    UserBookingInfoResponseDTO customerInfo(long customerId);

    boolean existsByUserId(long userId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO(u.id, u.firstName, u.lastName, u.email, u.avatar) FROM Customer c INNER JOIN User u ON c.userId = u.id WHERE c.id =:customerID")
    UserInfoResponseDTO getUserInfo(long customerID);
}
