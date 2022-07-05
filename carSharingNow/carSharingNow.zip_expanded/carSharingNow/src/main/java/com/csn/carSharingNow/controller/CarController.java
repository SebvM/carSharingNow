package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStation;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.CarStationRepository;
import org.hibernate.criterion.Example;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
//@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;
    @Autowired
    CarStationRepository carStationRepository;

    CarController() {

    }

    //@PreAuthorize("isMember()")
    @RequestMapping("/cars/all")
    public ResponseEntity<?> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(cars);
    }

    //@PreAuthorize("isMember()")
    @RequestMapping("/cars/{carStation:[A-Z-a-z- A-Z-a-z- 0-9-]+}")
    public ResponseEntity<?> getCarByStreet(@PathVariable String street) {
        List<Car> responseCars = new ArrayList<Car>();

        for (Car car : carRepository.findAll()) {
            if (car.getCarStationEnum().getStreet().equals(street)) {
                responseCars.add(car);
            }
        }return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseCars);

       /* for (Car car : carRepository.findAll()) {
            if (car.getCarStationEnum().equals(carStation)) {
                responseCars.add(car);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseCars);
        */

    }
    //@PreAuthorize("isMember()")
    @RequestMapping(path="/cars/{city:[A-za-z]+}/{street:[A-Z-a-z- A-Z-a-z- 0-9-]+}")
    public ResponseEntity<?> getCarByCityAndStreet(@PathVariable ("city") String city, @PathVariable ("street") String street) {//Map pathVariables) {

        List<Car> responseCars = new ArrayList<Car>();
        for (Car car : carRepository.findAll()) {
            if (car.getCarStationEnum().getCity().equals(city)
                    && car.getCarStationEnum().getStreet().equals(street)) {
                responseCars.add(car);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseCars);
    }
    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/cars")
    public ResponseEntity<?> newCar(@RequestBody Car newCar) {
        List<CarStation> carStationList = new ArrayList<CarStation>();

      //  if (Car.existsByStreet(newCar.getCarStationEnum()) {

            carRepository.save(newCar);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
      /*  } else {
            carRepository.save(newCar);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
*/
    }


}
