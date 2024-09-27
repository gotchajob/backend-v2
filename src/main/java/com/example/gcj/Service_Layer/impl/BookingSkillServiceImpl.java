package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Booking;
import com.example.gcj.Repository_Layer.model.BookingSkill;
import com.example.gcj.Repository_Layer.repository.BookingRepository;
import com.example.gcj.Repository_Layer.repository.BookingSkillRepository;
import com.example.gcj.Repository_Layer.repository.ExpertSkillOptionRepository;
import com.example.gcj.Service_Layer.service.BookingSkillService;
import com.example.gcj.Shared.exception.CustomException;
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
            throw new CustomException("không tìm thấy buổi hẹn");
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
