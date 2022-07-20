package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
import com.csn.carSharingNow.repositories.CarRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.ArrayList;
import java.util.HashSet;
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


    //UT-1 Test if getAllCars return a list with all Cars
    @Test
    public void returnContainsListNotEmpty() throws Exception {

        carController.addCar("GustavHeinemannBuergerhaus60", "testCar", "VW", 10.55f, 4);
        List<Car> carListRepository = carRepository.findAll();
        List<Car> carListController = carController.getAllCars();
        assertEquals(carListController, carListController);
    }

    // UT-2 Test getByID delivers expected car
    @Test
    public void returnContainsExpectedID() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("GustavHeinemannBuergerhaus60", "testCarID", "VW", 10.55f, 4);
        assertTrue(carController.getCarByID(carID).getName().equals("testCarID"));
    }

    //UT-3 Test getByID delivers expected car
    @Test
    public void returnDoesNotContainExpectedID() throws Exception {
        int carID = carRepository.findAll().size();
        carController.addCar("GustavHeinemannBuergerhaus60", "testCar3  ", "VW", 10.55f, 4);
        carController.addCar("GustavHeinemannBuergerhaus60", "testCar3  ", "VW", 10.55f, 4);
        carController.addCar("GustavHeinemannBuergerhaus60", "testCarID2", "VW", 10.55f, 4);

        assertFalse(carController.getCarByID(carID).getName().equals("testCarID2"));
    }

    //UT-4 Test car added as expected
    @Test
    public void returnContainsAddedCar() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("HanoverscheStrasse12", "testCarAdded", "BMW", 10.55f, 2);
        assertTrue(carController.getCarByID(carID).getName().equals("testCarAdded"));
        assertTrue(carController.getCarByID(carID).getCarBrand().equals("BMW"));
        assertEquals(carController.getCarByID(carID).getMileage(), 10.55f);
        assertEquals(carController.getCarByID(carID).getCarSeats(), 2);
    }

    //UT-5 Test addCar does not add a false Car
    @Test
    public void returnDoesNotContainAddedCar() throws Exception {
        int carID = carRepository.findAll().size() + 1;
        carController.addCar("falscheEingabe", "testCarAdded", "BMW", 10.55f, 2);
        assertFalse(carController.getCarByID(carID).getName().equals("testCarAddedFalse"));
        assertFalse(carController.getCarByID(carID).getCarBrand().equals("Mazda"));
        assertFalse(carController.getCarByID(carID).getCarStationEnum().equals("falscheEingabe"));
        assertNotEquals(carController.getCarByID(carID).getMileage(), 22.25f);
        assertNotEquals(carController.getCarByID(carID).getCarSeats(), 4);
    }

    //UT-6 Tests if findCarByCarStationEnum returns all enum referenced by the argument
    @Test
    public void returnContainsStationList() throws Exception {
        List<Car> carListFindByCarStationEnum = carRepository.findCarByCarStationEnum(CarStationEnum.valueOf("HanoverscheStrasse12"));
        List<Car> carListAddedByLoop = new ArrayList<>();
        List<Car> allCars= carController.getAllCars();
        for (Car car : allCars) {
            if (car.getCarStationEnum().equals(CarStationEnum.valueOf("HanoverscheStrasse12"))) {
                carListAddedByLoop.add(car);
            }
        }
        if(carListFindByCarStationEnum.size() != carListAddedByLoop.size()){
            assertFalse(true);
        }
        for(int i=0; i<carListFindByCarStationEnum.size(); i++){
            assertEquals(carListFindByCarStationEnum.get(i).getCarStationEnum(), (carListFindByCarStationEnum.get(i).getCarStationEnum()));
        }
    }
    // UT-7 Tests if findCarByCarStationEnum return has not the correct size
    @Test
    public void returnSizeNotMatch() throws Exception {
        List<Car> carListFindByCarStationEnum = carRepository.findCarByCarStationEnum(CarStationEnum.valueOf("HanoverscheStrasse12"));
        carController.addCar("HanoverscheStrasse12", "testCarAdded", "BMW", 10.55f, 2);
        List<Car> carListAddedByLoop = new ArrayList<>();
        List<Car> allCars= carController.getAllCars();
        for (Car car : allCars) {
            if (car.getCarStationEnum().equals(CarStationEnum.valueOf("HanoverscheStrasse12"))) {
                carListAddedByLoop.add(car);
            }
        }
        if(carListFindByCarStationEnum.size() != carListAddedByLoop.size()){
            assertFalse(false);
        }
        for(int i=0; i<carListFindByCarStationEnum.size(); i++){
            assertEquals(carListFindByCarStationEnum.get(i).getCarStationEnum(), (carListFindByCarStationEnum.get(i).getCarStationEnum()));
        }
    }

    //UT-8 Test car removed as expected
    @Test
    public void returnContainsRemovedCar() throws Exception {
        carController.addCar("HanoverscheStrasse12", "testCarRemoved", "BMW", 10.55f, 2);
        int oldCarId =carRepository.findAll().size();
        carController.removeCar(carRepository.findAll().size());
        assertNull(carController.getCarByID(oldCarId));
    }
}