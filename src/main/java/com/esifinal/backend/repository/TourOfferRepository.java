package com.esifinal.backend.repository;

import com.esifinal.backend.model.TourOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourOfferRepository extends JpaRepository<TourOffer, Long> {
    Optional<TourOffer> findByIdAndUser_Username(Long id, String username);
}
