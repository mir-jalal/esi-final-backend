package com.esifinal.backend.model;

import com.esifinal.backend.model.enums.TourOfferStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "tour_offers")
public class TourOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "profit")
    private BigDecimal profit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TourOfferStatus status;
}
