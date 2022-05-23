package com.esifinal.backend.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourCreateOfferDto {
    private Long tourId;
    private BigDecimal price;
}
