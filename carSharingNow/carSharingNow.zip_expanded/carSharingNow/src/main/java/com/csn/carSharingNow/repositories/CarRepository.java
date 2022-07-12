package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long > {
    Car findCarById(Long id);

    List<Car> findCarByCarStationEnum(String CarStationEnum);

}
