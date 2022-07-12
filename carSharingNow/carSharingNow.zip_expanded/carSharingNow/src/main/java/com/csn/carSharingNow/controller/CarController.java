package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class CarController {

    @Autowired
    CarRepository carRepository;

    public CarController() {

    }


    public List<Car> getAllCars() {
        List<Car> carList = carRepository.findAll();
        return carList;
    }

    public Car getCarByID(int Id) {
        Car car = carRepository.findCarById(Id);
        return car;
    }
    
    public List<Car> getCarsByCarStationEnum(String CarStationEnum){
        List<Car> carList = carRepository.findCarByCarStationEnum(CarStationEnum);
        return carList;
    }

}
