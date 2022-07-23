package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
import com.csn.carSharingNow.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class CarController {

    @Autowired
    CarRepository carRepository;


    public List<Car> getAllCars() {
        List<Car> carList = carRepository.findAll();
        return carList;
    }

    public Car getCarById(int Id) {
        Car car = carRepository.findCarById(Id);
        return car;
    }
    public void addCar(Car car){
        carRepository.save(car);
    }
    public void removeCar(int Id){
        carRepository.delete(carRepository.getReferenceById(Id));
    }
    
    public List<Car> getCarsByCarStationEnum(CarStationEnum CarStationEnum){
        List<Car> carList = carRepository.findCarByCarStationEnum(CarStationEnum);
        return carList;
    }

	public void addCar(Car car) {
		carRepository.save(car);		
	}

}
