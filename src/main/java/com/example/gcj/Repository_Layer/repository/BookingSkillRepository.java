package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSkillRepository extends JpaRepository<BookingSkill, Long> {

    @Query("SELECT b FROM BookingSkill b WHERE b.id =:id")
    BookingSkill findById(long id);

    boolean existsByBookingIdAndExpertSkillOptionId(long bookingId, long expertSkillOptionId);
}
