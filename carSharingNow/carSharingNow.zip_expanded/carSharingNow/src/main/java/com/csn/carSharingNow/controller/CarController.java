package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CarController {

    @Autowired
    CarRepository carRepository;

    public CarController() {

    }


    public List getAllCars() {
        List<Car> carList = carRepository.findAll();
        return carList;
    }
    public Car getCarById(int id){
        return carRepository.findCarById(id);
    }
    public List getCarsBySeats(int seats){
        List<Car> carList = carRepository.findCarBySeats(seats);
        return carList;
    }

    public List getCarsByCityAndStreet(String city, String street){
        List<Car> carList = carRepository.findCarsByCityAndStreet(city,street);
        return carList;
    }
    public List getCarsByStreet(String street){
        List<Car> carList = carRepository.findCarByStreet(street);
        return carList;
    }

    public List getCarsByCarStationEnum(String CarStationEnum){
        List<Car> carList = carRepository.findCarByCarStationEnum(CarStationEnum);
        return carList;
    }
}
