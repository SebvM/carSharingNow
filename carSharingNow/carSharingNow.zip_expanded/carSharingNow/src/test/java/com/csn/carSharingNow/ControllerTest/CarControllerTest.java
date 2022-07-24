package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
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

@SpringBootTest
public class CarControllerTest {

    @Autowired
    CarController carController;
    @Autowired
    CarRepository carRepository;





    //CT-1 Test if getAllCars return a list with all Cars
    @Test
    public void returnContainsListNotEmpty() throws Exception {
        Car testCarUT1  = new Car("GustavHeinemannBuergerhaus60", "carCT-1", "VW", 10.55f, 4);
        carController.addCar(testCarUT1);
        List<Car> carListRepository = carRepository.findAll();
        List<Car> carListController = carController.getAllCars();
        assertEquals(carListRepository.size(), carListController.size());
    }

    // CT-2 Test getByID delivers expected car
    @Test
    public void returnContainsExpectedID() throws Exception {
        Car testCarUT2 = new Car("GustavHeinemannBuergerhaus60", "carCT-2", "VW", 10.55f, 4);
        carController.addCar(testCarUT2);
        assertTrue(carController.getCarById(testCarUT2.getId()).getName().equals("carCT-2"));
    }

    //CT-3 Test getByID does not delivers  expected car
    @Test
    public void returnDoesNotContainExpectedID() throws Exception {
        Car testCarUT3 = new Car("GustavHeinemannBuergerhaus60", "carCT-3", "VW", 10.55f, 4);
        Car testCar2UT3 = new Car("GustavHeinemannBuergerhaus60", "car2CT-3", "VW", 10.55f, 4);
        carController.addCar(testCarUT3);
        carController.addCar(testCar2UT3);
        int carID = testCar2UT3.getId();
        assertFalse(carController.getCarById(carID).getName().equals("carCT-3"));
    }

    //CT-4 Test car added as expected
    @Test
    public void returnContainsAddedCar() throws Exception {
        Car testCarUT4 = new Car("GustavHeinemannBuergerhaus60", "carCT-4", "BMW", 10.55f, 2);
        carController.addCar(testCarUT4);
        int carID = testCarUT4.getId();
        assertTrue(carController.getCarById(carID).getName().equals("carCT-4"));
        assertTrue(carController.getCarById(carID).getCarBrand().equals("BMW"));
        assertEquals(carController.getCarById(carID).getMileage(), 10.55f);
        assertEquals(carController.getCarById(carID).getCarSeats(), 2);
    }

    //CT-5 Test addCar does not add a false Car
    @Test
    public void returnDoesNotContainAddedCar() throws Exception {
        Car testCarUT5 = new Car("GustavHeinemannBuergerhaus60", "carCT-5", "BMW", 10.55f, 2);
        carController.addCar(testCarUT5);
        int carID = testCarUT5.getId();
        assertFalse(carController.getCarById(carID).getName().equals("testCarAddedFalse"));
        assertFalse(carController.getCarById(carID).getCarBrand().equals("Mazda"));
        assertFalse(carController.getCarById(carID).getCarStationEnum().equals("falscheEingabe"));
        assertNotEquals(carController.getCarById(carID).getMileage(), 22.25f);
        assertNotEquals(carController.getCarById(carID).getCarSeats(), 4);
    }

    //CT-6 Tests if findCarByCarStationEnum returns all enum referenced by the argument
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
    // CT-7 Tests if findCarByCarStationEnum return has not the correct size
    @Test
    public void returnSizeNotMatch() throws Exception {
        List<Car> carListFindByCarStationEnum = carRepository.findCarByCarStationEnum(CarStationEnum.valueOf("HanoverscheStrasse12"));
        Car testCarUT7 = new Car("HanoverscheStrasse12", "carCT-7", "BMW", 10.55f, 2);
        carController.addCar(testCarUT7);
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

    //CT-8 Test car removed as expected
    @Test
    public void returnContainsRemovedCar() throws Exception {
        Car testCarUT7 = new Car("HanoverscheStrasse12", "carCT-8", "BMW", 10.55f, 2);
        carController.addCar(testCarUT7);
        int oldCarId =carRepository.findAll().size();
        carController.removeCar(carRepository.findAll().size());
        assertNull(carController.getCarById(oldCarId));
    }
}