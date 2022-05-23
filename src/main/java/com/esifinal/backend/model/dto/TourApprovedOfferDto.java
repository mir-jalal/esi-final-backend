package com.esifinal.backend.model.dto;

import com.esifinal.backend.model.Tour;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourApprovedOfferDto {
    private BigDecimal price;
    private BigDecimal profit;
    private Tour tour;
}
