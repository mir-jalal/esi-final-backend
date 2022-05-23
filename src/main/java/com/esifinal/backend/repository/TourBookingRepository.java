package com.esifinal.backend.repository;

import com.esifinal.backend.model.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourBookingRepository extends JpaRepository<TourBooking, Long> {
}
