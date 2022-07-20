package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer > {
    Car findCarById(int id);

    List<Car> findCarByCarStationEnum(CarStationEnum carStationEnum);

}
