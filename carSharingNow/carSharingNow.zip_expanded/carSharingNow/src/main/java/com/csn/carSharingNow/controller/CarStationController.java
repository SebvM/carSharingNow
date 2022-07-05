package com.csn.carSharingNow.controller;

import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStation;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.CarStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


//@CrossOrigin(origins = "*")
@RestController
public class CarStationController {

    @Autowired
    CarStationRepository carStationRepository;

    CarStationController() {

    }

    //@PreAuthorize("isAdmin()")
    @RequestMapping("/carStations/all")
    public ResponseEntity<?> getAllCarStations() {
        List<CarStation> carStations = carStationRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(carStations);
    }

    //@PreAuthorize("isMember()")
    @RequestMapping("/carStations/{city:[A-Z-a-z- A-Z-a-z- 0-9-]+}")
    public ResponseEntity<?> getCarStationByCity(@PathVariable String city) {
        List<CarStation> responseCarStations = new ArrayList<CarStation>();

        for (CarStation carStation : carStationRepository.findAll()) {
            if (carStation.getCity().equals(carStation)) {
                responseCarStations.add(carStation);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseCarStations);

    }


    @PostMapping(path = "/carStations")
    public ResponseEntity<?> newCar(@RequestBody CarStation newCarStation) {
        List<CarStation> responseCarStations = new ArrayList<CarStation>();
      /*  if (carStationRepository.findAll() != null) {
            for (CarStation carStation : carStationRepository.findAll()) {
                if (newCarStation.getCity().equals(carStation.getCity())
                        && newCarStation.getStreet().equals(carStation.getStreet())
                        && newCarStation.getHouseNumber().equals(carStation.getHouseNumber())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }
        } else {*/
            carStationRepository.save(newCarStation);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        //}
        //return null;
    }
}
