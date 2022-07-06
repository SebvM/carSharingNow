package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer > {
    Reservation findByReservationID(int id);
    Reservation removeByReservationID(int id);
}
