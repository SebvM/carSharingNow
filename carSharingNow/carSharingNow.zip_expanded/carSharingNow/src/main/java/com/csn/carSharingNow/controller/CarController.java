package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
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
    public void addCar(String carStationEnum,String name, String carBrand, float mileage, int carSeats){
        Car newCar = new Car( carStationEnum, name,  carBrand,  mileage,  carSeats);
        carRepository.save(newCar);
    }
    public void removeCar(int Id){
        carRepository.delete(carRepository.getReferenceById(Id));
    }
    
    public List<Car> getCarsByCarStationEnum(CarStationEnum CarStationEnum){
        List<Car> carList = carRepository.findCarByCarStationEnum(CarStationEnum);
        return carList;
    }

}
