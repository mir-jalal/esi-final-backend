package com.esifinal.backend.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourBookingDto {
    private Long id;
    private String name;
    private String surname;
    private Long tourId;
    private BigDecimal totalPrice;
}
