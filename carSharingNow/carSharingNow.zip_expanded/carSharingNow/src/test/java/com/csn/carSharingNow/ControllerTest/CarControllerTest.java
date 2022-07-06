package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CarControllerTest {

    @Autowired
    private CarController carController = new CarController();


    @ParameterizedTest
    @CsvSource(value={"GustavHeinemannBuergerhaus60,VW,60.253,4","GustavHeinemannBuergerhaus60,BMW,30.253,4"})
    public void returnContainsListWithTwoCars(String carStation, String carBrand, float mileage, int carSeats) throws Exception {
        Car newCar = new Car(carStation,carBrand,mileage,carSeats);
        carController.newCar(newCar);
        List<Car> carList =carController.getAllCars();
    }
}
