package com.esifinal.backend.service;

import com.esifinal.backend.model.Tour;
import com.esifinal.backend.model.dto.TourDto;
import com.esifinal.backend.repository.TourRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {

    private final ObjectMapper objectMapper;
    private final TourRepository repository;

    public Tour create(TourDto tourDto) {
        return repository.save(objectMapper.convertValue(tourDto, Tour.class));
    }

    public List<Tour> findAll() {
        return repository.findAll();
    }

    public Tour update(Long id, TourDto tourDto) {
        Tour tour = repository.findById(id).orElseThrow();
        if(tourDto.getName() != null) tour.setName(tourDto.getName());
        if(tourDto.getLocation() != null) tour.setLocation(tourDto.getLocation());
        if(tourDto.getStartTime() != null) tour.setStartTime(tourDto.getStartTime());
        if(tourDto.getFinishTime() != null) tour.setFinishTime(tourDto.getFinishTime());
        return repository.save(tour);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Tour findById(Long tourId) {
        return repository.findById(tourId).orElseThrow();
    }
}
