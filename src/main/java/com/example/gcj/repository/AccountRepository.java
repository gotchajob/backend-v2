package com.example.gcj.repository;

import com.example.gcj.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(long id);
    Account findByUserId(long id);
    @Query("SELECT a.id FROM Account a WHERE a.user.id = :userId")
    Long getAccountIdByUserId(@Param("userId") long userId);

}
