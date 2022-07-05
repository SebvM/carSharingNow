package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.CarStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarStationRepository extends JpaRepository<CarStation, Integer > {
    Boolean existsByStreet(String street);
}
