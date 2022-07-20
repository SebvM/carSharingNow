package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer > {
    Reservation findById(int id);
    void removeById(int id);
    List<Optional<Reservation>>findByuser_id(Long id);
}
