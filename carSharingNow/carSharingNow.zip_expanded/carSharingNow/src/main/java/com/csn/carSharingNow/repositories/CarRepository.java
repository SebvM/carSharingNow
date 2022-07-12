package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long > {
    Car findCarById(Long id);

    List<Car> findCarByCarStationEnum(String CarStationEnum);

}
