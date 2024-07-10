package com.example.gcj.service.impl;

import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Booking;
import com.example.gcj.model.BookingSkill;
import com.example.gcj.repository.BookingRepository;
import com.example.gcj.repository.BookingSkillRepository;
import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.service.BookingSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingSkillServiceImpl implements BookingSkillService {
    private final BookingSkillRepository bookingSkillRepository;
    private final BookingRepository bookingRepository;
    private final ExpertSkillOptionRepository expertSkillOptionRepository;

    @Override
    public boolean add(List<Long> idList, long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new CustomException("not found booking with id " + bookingId + " when create booking skill");
        }


        List<BookingSkill> list = new ArrayList<>();
        for (long id : idList) {
            boolean isExisted = bookingSkillRepository.existsByBookingIdAndExpertSkillOptionId(bookingId, id);
            if (isExisted) {
                continue;
            }

            if (!expertSkillOptionRepository.existsById(id)) {
                continue;
            }

            BookingSkill build = BookingSkill
                    .builder()
                    .booking(new Booking(bookingId))
                    .expertSkillOptionId(id)
                    .build();

            list.add(build);

        }

        bookingSkillRepository.saveAll(list);
        return true;
    }
}
