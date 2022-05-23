package com.esifinal.backend.service;

import com.esifinal.backend.model.TourBooking;
import com.esifinal.backend.model.TourOffer;
import com.esifinal.backend.model.User;
import com.esifinal.backend.model.dto.TourBookingDto;
import com.esifinal.backend.model.enums.TourOfferStatus;
import com.esifinal.backend.repository.TourBookingRepository;
import com.esifinal.backend.repository.TourOfferRepository;
import com.esifinal.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TourBookingService {
    private final TourBookingRepository repository;
    private final UserRepository userRepository;
    private final TourOfferRepository tourOfferRepository;
    private final ObjectMapper objectMapper;

    public TourBookingDto create(TourBookingDto createBookingDto) {
        TourOffer tourOffer = tourOfferRepository.findById(createBookingDto.getTourId()).orElseThrow();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if (tourOffer.getStatus() != TourOfferStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't find tour");
        }
        TourBookingDto booking = objectMapper.convertValue(repository.save(
                TourBooking.builder()
                        .name(createBookingDto.getName())
                        .surname(createBookingDto.getSurname())
                        .user(user)
                        .tourOffer(tourOffer)
                        .build()
        ), TourBookingDto.class);
        booking.setTotalPrice(tourOffer.getPrice().add(tourOffer.getProfit()));
        return booking;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}

