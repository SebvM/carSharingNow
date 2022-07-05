package com.csn.carSharingNow.repositories;

import com.csn.carSharingNow.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer > {
}
