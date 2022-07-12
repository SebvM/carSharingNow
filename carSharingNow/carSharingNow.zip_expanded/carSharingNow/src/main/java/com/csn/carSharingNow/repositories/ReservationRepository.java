package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer > {
    Reservation findById(int id);
    Reservation removeById(int id);
    List<Optional<Reservation>>findByUserID(long userId);
}
