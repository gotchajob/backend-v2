package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingRejectSuggest;
import com.example.gcj.Repository_Layer.repository.BookingRejectSuggestRepository;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.CreateBookingRejectSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.UpdateBookingRejectSuggestRequestDTO;
import com.example.gcj.Service_Layer.service.BookingRejectSuggestService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingRejectSuggestServiceImpl implements BookingRejectSuggestService {
    private final BookingRejectSuggestRepository bookingRejectSuggestRepository;

    @Override
    public boolean delete(long id) {
        if (!bookingRejectSuggestRepository.existsById(id)) {
            throw new CustomException("not found booking reject suggest with id " + id);
        }

        bookingRejectSuggestRepository.deleteById(id);

        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingRejectSuggestRequestDTO request) {
        BookingRejectSuggest bookingRejectSuggest = bookingRejectSuggestRepository.findById(id);

        if (bookingRejectSuggest == null) {
            throw new CustomException("not found booking reject suggest with id " + id);
        }

        if (request.getType() < 1 || request.getType() > 2) {
            throw new CustomException("invalid type");
        }

        bookingRejectSuggest.setContent(request.getContent());
        bookingRejectSuggest.setDescription(request.getDescription());
        bookingRejectSuggest.setType(request.getType());

        bookingRejectSuggestRepository.save(bookingRejectSuggest);

        return true;
    }

    @Override
    public boolean create(CreateBookingRejectSuggestRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        BookingRejectSuggest build = BookingRejectSuggest
                .builder()
                .content(request.getContent())
                .description(request.getDescription())
                .type(request.getType())
                .build();
        bookingRejectSuggestRepository.save(build);

        return true;
    }

    @Override
    public BookingRejectSuggestResponseDTO getById(long id) {
        BookingRejectSuggest bookingRejectSuggest = bookingRejectSuggestRepository.findById(id);
        if (bookingRejectSuggest == null) {
            throw new CustomException("not found booking reject suggest with id " + id);
        }


        return BookingRejectSuggestResponseDTO
                .builder()
                .id(bookingRejectSuggest.getId())
                .content(bookingRejectSuggest.getContent())
                .description(bookingRejectSuggest.getDescription())
                .type(bookingRejectSuggest.getType())
                .createdAt(bookingRejectSuggest.getCreatedAt())
                .updatedAt(bookingRejectSuggest.getUpdatedAt())
                .build();
    }

    @Override
    public List<BookingRejectSuggestListResponseDTO> get(Integer type) {
        if (type != null && (type < 1 || type > 2)) {
            throw new CustomException("invalid type");
        }

        List<BookingRejectSuggest> list = type == null ?
                bookingRejectSuggestRepository.findAll()
                : bookingRejectSuggestRepository.findByType(type);

        return list.stream().map(i -> BookingRejectSuggestListResponseDTO
                .builder()
                .id(i.getId())
                .content(i.getContent())
                .build()).toList();
    }
}
