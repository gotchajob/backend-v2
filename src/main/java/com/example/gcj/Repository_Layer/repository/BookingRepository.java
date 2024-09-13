package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Booking;
import com.example.gcj.Service_Layer.dto.dash_board.BookingDashboardResponseDTO;
import com.example.gcj.Service_Layer.dto.dash_board.TopExpertResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.id =:id")
    Booking findById(long id);

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.customerId =:customerId")
    List<Booking> getByCustomerId(long customerId);
    List<Booking> getByCustomerIdAndStatus(long customerId, int status);

    @Query("SELECT b FROM Booking b WHERE b.status != 0 and b.expertId =:expertId")
    List<Booking> getByExpertId(long expertId);
    List<Booking> getByExpertIdAndStatus(long expertId, int status);
    List<Booking> findByStatus(int status);
    List<Booking> findByStatusAndExpireCompleteDateBefore(int status, LocalDateTime now);

    long count();
    long countByStatus(int status);

    @Query("SELECT count(DISTINCT b.customerCvId) FROM Booking b WHERE b.status = :status")
    Long countDistinctCvId(@Param("status") int status);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.dash_board.TopExpertResponseDTO(e.id, u.avatar, CONCAT(u.firstName, ' ', u.lastName), null, count(b.id)) " +
            "FROM Booking b INNER JOIN Expert e ON b.expertId = e.id " +
            "INNER JOIN User u ON e.user.id = u.id " +
            "WHERE b.status = :status " +
            "GROUP BY b.expertId")
    List<TopExpertResponseDTO> topExpert(int status, Pageable pageable);
    @Query("SELECT new com.example.gcj.Service_Layer.dto.dash_board.BookingDashboardResponseDTO(" +
            "MONTH(b.createdAt) as month, " +
            "SUM(CASE WHEN b.status = 5 THEN 1 ELSE 0 END) as countStatus1, " +
            "SUM(CASE WHEN b.status = 6 THEN 1 ELSE 0 END) as countStatus2, " +
            "SUM(CASE WHEN b.status = 7 THEN 1 ELSE 0 END) as countStatus3) " +
            "FROM Booking b " +
            "WHERE b.status IN (5, 6, 7) AND YEAR(b.createdAt) =:year " +
            "GROUP BY MONTH(b.createdAt) " +
            "ORDER BY month")
    List<BookingDashboardResponseDTO> bookingDashboardByStatusEachMonth(int year);


}
