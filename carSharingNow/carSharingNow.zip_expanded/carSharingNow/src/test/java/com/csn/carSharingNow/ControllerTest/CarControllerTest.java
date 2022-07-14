package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author Tristan Lilienthal
 *
 */

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class CarControllerTest {


    @Autowired
    CarController carController;
    @Autowired
    CarRepository carRepository;

    //Test list is empty
   /* @Test
    public void returnContainsEmptyList() throws Exception {
        assertFalse(carController.getAllCars().isEmpty());
    }*/
    //Testlist contains an added car
    @Test
    public void returnContainsListNotEmpty() throws Exception {

        carController.addCar("GustavHeinemannBuergerhaus60", "testCar", "VW", 10.55f, 4);
        List<Car> carListRepository = carRepository.findAll();
        List<Car> carListController = carController.getAllCars();
        assertEquals(carListController, carListController);
    }

    //Test getByID delivers expected car
    @Test
    public void returnContainsExpectedID() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("GustavHeinemannBuergerhaus60", "testCarID", "VW", 10.55f, 4);
        assertTrue(carController.getCarByID(carID).getName().equals("testCarID"));
    }

    //Test getByID delivers expected car
    @Test
    public void returnDoesNotContainExpectedID() throws Exception {
        int carID = carRepository.findAll().size();
        carController.addCar("GustavHeinemannBuergerhaus60", "testCar3  ", "VW", 10.55f, 4);
        carController.addCar("GustavHeinemannBuergerhaus60", "testCar3  ", "VW", 10.55f, 4);
        carController.addCar("GustavHeinemannBuergerhaus60", "testCarID2", "VW", 10.55f, 4);

        assertFalse(carController.getCarByID(carID).getName().equals("testCarID2"));
    }

    //Test car added as expected
    @Test
    public void returnContainsAddedCar() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("HanoverscheStrasse12", "testCarAdded", "BMW", 10.55f, 2);
        assertTrue(carController.getCarByID(carID).getName().equals("testCarAdded"));
        assertTrue(carController.getCarByID(carID).getCarBrand().equals("BMW"));
        assertEquals(carController.getCarByID(carID).getMileage(), 10.55f);
        assertEquals(carController.getCarByID(carID).getCarSeats(), 2);
    }

    //Test car added as expected
    @Test
    public void returnDoesNotContainAddedCar() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("HanoverscheStrasse12", "testCarAdded", "BMW", 10.55f, 2);
        assertFalse(carController.getCarByID(carID).getName().equals("testCarAddedFalse"));
        assertFalse(carController.getCarByID(carID).getCarBrand().equals("Mazda"));
        assertFalse(carController.getCarByID(carID).getCarStationEnum().equals("Bahnhofstraße2"));
        assertNotEquals(carController.getCarByID(carID).getMileage(), 22.25f);
        assertNotEquals(carController.getCarByID(carID).getCarSeats(), 4);
    }
}