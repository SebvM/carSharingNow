package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long > {
    Optional<Reservation> findById(Long id);
    Reservation removeById(Long id);
    List<Optional<Reservation>>findByuserId(Long userId);
}
