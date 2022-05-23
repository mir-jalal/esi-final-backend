package com.esifinal.backend.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TourBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @JoinColumn(name = "tour_offer_id", nullable = false)
    @ManyToOne
    private TourOffer tourOffer;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;
}
