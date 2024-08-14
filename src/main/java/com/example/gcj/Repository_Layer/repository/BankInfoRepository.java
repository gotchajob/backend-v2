package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
    @Query("SELECT bi FROM BankInfo bi WHERE bi.status != 0 AND bi.id =:id ")
    BankInfo findById(long id);
    @Query("SELECT bi FROM BankInfo bi WHERE bi.status != 0 AND bi.accountId =:accountId")
    List<BankInfo> findByAccountId(long  accountId);
    @Query("SELECT bi FROM BankInfo bi WHERE bi.status != 0")
    List<BankInfo> findAll();
}
