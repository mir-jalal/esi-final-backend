package com.esifinal.backend.model.dto;

import com.esifinal.backend.model.enums.TourOfferStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourOfferDto {
    private Long id;
    private BigDecimal price;
    private TourOfferStatus status;
}
