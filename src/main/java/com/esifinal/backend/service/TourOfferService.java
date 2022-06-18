package com.esifinal.backend.service;

import com.esifinal.backend.model.TourOffer;
import com.esifinal.backend.model.dto.TourApprovedOfferDto;
import com.esifinal.backend.model.dto.TourCreateOfferDto;
import com.esifinal.backend.model.dto.TourOfferDto;
import com.esifinal.backend.model.enums.TourOfferStatus;
import com.esifinal.backend.repository.TourOfferRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TourOfferService {

    private final TourOfferRepository repository;
    private final UserService userService;
    private final TourService tourService;
    private final ObjectMapper objectMapper;

    public TourOfferDto findById(Long id) {
        return objectMapper.convertValue(repository.findById(id).orElseThrow(), TourOfferDto.class);
    }

    public TourOfferDto create(TourCreateOfferDto tourCreateOfferDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext();

        TourOffer tour = objectMapper.convertValue(tourCreateOfferDto, TourOffer.class);
        tour.setUser(userService.findUserByUsername(userDetails.getUsername()));
        tour.setStatus(TourOfferStatus.CREATED);
        tour.setPrice(tourCreateOfferDto.getPrice());
        tour.setTour(tourService.findById(tourCreateOfferDto.getTourId()));

        return objectMapper.convertValue(repository.save(tour), TourOfferDto.class);
    }

    public TourOfferDto update(TourCreateOfferDto tourCreateOfferDto, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext();
        TourOffer tourOffer = repository.findByIdAndUser_Username(id, userDetails.getUsername()).orElseThrow();

        if (tourOffer.getStatus() == TourOfferStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update approved offer");
        }
        if (tourCreateOfferDto.getPrice() != null) tourOffer.setPrice(tourCreateOfferDto.getPrice());
        return objectMapper.convertValue(repository.save(tourOffer), TourOfferDto.class);
    }

    public void delete(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext();
        TourOffer tourOffer = repository.findByIdAndUser_Username(id, userDetails.getUsername()).orElseThrow();

        if (tourOffer.getStatus() == TourOfferStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update approved offer");
        }
        repository.deleteById(id);
    }

    public TourApprovedOfferDto approve(Long id, BigDecimal profit) {
        TourOffer tourOffer = repository.findById(id).orElseThrow();
        tourOffer.setProfit(profit);
        tourOffer.setStatus(TourOfferStatus.APPROVED);
        return objectMapper.convertValue(repository.save(tourOffer), TourApprovedOfferDto.class);
    }
}
