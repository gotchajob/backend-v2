package com.example.gcj.service.impl;

import com.example.gcj.repository.BookingExpertFeedbackRepository;
import com.example.gcj.service.BookingExpertFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingExpertFeedbackServiceIml implements BookingExpertFeedbackService {
    private final BookingExpertFeedbackRepository bookingExpertFeedbackRepository;

}
