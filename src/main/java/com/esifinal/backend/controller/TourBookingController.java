package com.esifinal.backend.controller;

import com.esifinal.backend.model.dto.TourBookingDto;
import com.esifinal.backend.service.TourBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@PreAuthorize("hasRole('GUEST')")
public class TourBookingController {
    private final TourBookingService service;

    @PostMapping
    public ResponseEntity<TourBookingDto> createBooking(@RequestBody TourBookingDto createBookingDto){
        return new ResponseEntity<>(service.create(createBookingDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TourBookingDto> deleteBooking(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
