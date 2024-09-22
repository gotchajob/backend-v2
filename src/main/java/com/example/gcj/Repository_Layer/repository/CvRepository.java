package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    @Query("SELECT c FROM Cv c WHERE c.id = :id and c.status != 0")
    Cv getById(long id);

    @Query("SELECT c FROM Cv c WHERE c.customerId = :customerId and c.status != 0")
    List<Cv> findByCustomerId(@Param("customerId") long customerId);

    @Query("SELECT cc.id, cc.name, COALESCE(COUNT(c.id), 0) " +
            "FROM CvCategory cc " +
            "LEFT JOIN CvTemplate ct ON ct.categoryId = cc.id " +
            "LEFT JOIN Cv c ON c.cvTemplateId = ct.id AND c.status != 0 " +
            "GROUP BY cc.id, cc.name")
    List<Object[]> countCvDashboard();

    long countByStatus(int status);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status AND MONTH(b.createdAt) = :month")
    Long countByStatusAndCreatedAtMonth(@Param("status") int status, @Param("month") int month);

    long countByStatusNotAndCustomerId(int status, long customer);

    @Query("SELECT cc.id FROM Cv c INNER JOIN CvTemplate ct ON c.cvTemplateId = ct.id INNER JOIN CvCategory cc ON ct.categoryId = cc.id WHERE c.id =:cvId ")
    long getCvCategoryId(long cvId);
}
