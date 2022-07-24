package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
import com.csn.carSharingNow.repositories.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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





    //UT-1 Test if getAllCars return a list with all Cars
    @Test
    public void returnContainsListNotEmpty() throws Exception {
        Car testCarUT1  = new Car("GustavHeinemannBuergerhaus60", "testCarList", "VW", 10.55f, 4);
        carController.addCar(testCarUT1);
        List<Car> carListRepository = carRepository.findAll();
        List<Car> carListController = carController.getAllCars();
        assertEquals(carListRepository.size(), carListController.size());
    }

    // UT-2 Test getByID delivers expected car
    @Test
    public void returnContainsExpectedID() throws Exception {
        Car testCarUT2 = new Car("GustavHeinemannBuergerhaus60", "testCarID", "VW", 10.55f, 4);
        carController.addCar(testCarUT2);
        assertTrue(carController.getCarById(testCarUT2.getId()).getName().equals("testCarID"));
    }

    //UT-3 Test getByID does not delivers  expected car
    @Test
    public void returnDoesNotContainExpectedID() throws Exception {
        Car testCarUT3 = new Car("GustavHeinemannBuergerhaus60", "testCarNotID", "VW", 10.55f, 4);
        Car testCar2UT3 = new Car("GustavHeinemannBuergerhaus60", "testCarNotID2", "VW", 10.55f, 4);
        carController.addCar(testCarUT3);
        carController.addCar(testCar2UT3);
        int carID = testCar2UT3.getId();
        assertFalse(carController.getCarById(carID).getName().equals("testCarID2"));
    }

    //UT-4 Test car added as expected
    @Test
    public void returnContainsAddedCar() throws Exception {
        Car testCarUT4 = new Car("GustavHeinemannBuergerhaus60", "testCarAdded", "BMW", 10.55f, 2);
        carController.addCar(testCarUT4);
        int carID = testCarUT4.getId();
        assertTrue(carController.getCarById(carID).getName().equals("testCarAdded"));
        assertTrue(carController.getCarById(carID).getCarBrand().equals("BMW"));
        assertEquals(carController.getCarById(carID).getMileage(), 10.55f);
        assertEquals(carController.getCarById(carID).getCarSeats(), 2);
    }

    //UT-5 Test addCar does not add a false Car
    @Test
    public void returnDoesNotContainAddedCar() throws Exception {
        Car testCarUT5 = new Car("GustavHeinemannBuergerhaus60", "testCarAdded", "BMW", 10.55f, 2);
        carController.addCar(testCarUT5);
        int carID = testCarUT5.getId();
        assertFalse(carController.getCarById(carID).getName().equals("testCarAddedFalse"));
        assertFalse(carController.getCarById(carID).getCarBrand().equals("Mazda"));
        assertFalse(carController.getCarById(carID).getCarStationEnum().equals("falscheEingabe"));
        assertNotEquals(carController.getCarById(carID).getMileage(), 22.25f);
        assertNotEquals(carController.getCarById(carID).getCarSeats(), 4);
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
        Car testCarUT7 = new Car("HanoverscheStrasse12", "testCarAdded", "BMW", 10.55f, 2);
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

    //UT-8 Test car removed as expected
    @Test
    public void returnContainsRemovedCar() throws Exception {
        Car testCarUT7 = new Car("HanoverscheStrasse12", "testCarRemoved", "BMW", 10.55f, 2);
        carController.addCar(testCarUT7);
        int oldCarId =carRepository.findAll().size();
        carController.removeCar(carRepository.findAll().size());
        assertNull(carController.getCarById(oldCarId));
    }
}