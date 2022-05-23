package com.esifinal.backend.controller;

import com.esifinal.backend.model.TourOffer;
import com.esifinal.backend.model.dto.TourApprovedOfferDto;
import com.esifinal.backend.model.dto.TourCreateOfferDto;
import com.esifinal.backend.model.dto.TourOfferDto;
import com.esifinal.backend.service.TourOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/offers")
public class TourOfferController {
    private final TourOfferService service;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<TourOfferDto> createOffer(@RequestBody TourCreateOfferDto tourCreateOfferDto){
        return new ResponseEntity<>(service.create(tourCreateOfferDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<TourOfferDto> updateOffer(
            @RequestBody TourCreateOfferDto tourOfferDto,
            @PathVariable Long id){
        return new ResponseEntity<>(service.update(tourOfferDto, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<TourOffer> deleteOffer(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourApprovedOfferDto> approveOffer(@PathVariable Long id, @RequestBody TourCreateOfferDto offerDto){
        return new ResponseEntity<>(service.approve(id, offerDto.getPrice()), HttpStatus.OK);
    }
}
