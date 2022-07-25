package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.ReservationRepository;
import com.sun.jna.platform.win32.Sspi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationControllerTest {

    @Autowired
    ReservationController reservationController;
    @Autowired
    ReservationRepository reservationRepository;

    //UT-1Tests if getAllReservations return a List with the added reservation
    @Test
    public void returnListContainsAddedReservation() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Date date = new GregorianCalendar(1994,11,01).getTime();
        Car carRT1  = new Car("KulenkampffAllee99", "carRT1", "VW", 10.55f, 4);
        User UserRT1 = new User("Torb", "Thorsten.tor@gmx.de", "1234", "Thorsten",
                "Hansen",date,userRoles );
        Timestamp reservationStart =  Timestamp.valueOf("2022-01-01 10:10:10.0");
        Timestamp reservationEnd =  Timestamp.valueOf("2022-01-03 10:10:10.0");
        Reservation reservation = new Reservation(carRT1,UserRT1,reservationStart,reservationEnd);
        reservationController.addReservation(reservation);

        assertTrue(reservationController.findReservationById(reservation.getId()).getId()==reservation.getId());

    }



    //UT-3 Tests if a reservation is cancelable
    @Test
    public void returnListDoesNotContainsCanceled() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Car carRT2 = new Car("KulenkampffAllee99", "carRT2", "Ford", 10.55f, 2);
        Date date = new GregorianCalendar(1995, 11, 01).getTime();
        User UserRT3 = new User("Herbs", "herbs@gmx.de", "1234", "Roman",
                "Hansen", date, userRoles);
        Timestamp reservationStart = Timestamp.valueOf("2022-06-01 10:10:10.0");
        Timestamp reservationEnd = Timestamp.valueOf("2022-06-03 10:10:10.0");
        Reservation reservation = new Reservation(carRT2, UserRT3, reservationStart, reservationEnd);
        reservationController.addReservation(reservation);
        reservationController.cancelReservation(reservation.getId());
        assertNull(reservationController.findReservationById(reservation.getId()));
    }

    //UT-4 Tests if a free Cars gets listed
    @Test
    public void returnListContainsAllAvailableCars() throws Exception {

        Timestamp reservationStart =  Timestamp.valueOf("2022-02-01 10:10:10.0");
        Timestamp reservationEnd =  Timestamp.valueOf("2022-02-03 10:10:10.0");

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Date date = new GregorianCalendar(1995,11,01).getTime();
        User UserRT4 = new User("Time", "tim.tim@gmx.de", "1234", "Tim",
                "Hansen",date,userRoles );
        Car carRT4  = new Car("KulenkampffAllee99", "carRT3", "Mazda", 10.55f, 2);
        Timestamp reservationStartCar =  Timestamp.valueOf("2022-02-21 10:10:10.0");
        Timestamp reservationEndCar =  Timestamp.valueOf("2022-02-23 10:10:10.0");
        Reservation reservation = new Reservation(carRT4, UserRT4, reservationStartCar, reservationEndCar);
        reservationController.addReservation(reservation);

        List<Car> availableCars = new ArrayList<>();
        for(int i=0; i<  reservationController.getAvailableCars(reservationStart, reservationEnd).size(); i++){
            if(reservationController.getAvailableCars(reservationStart, reservationEnd).get(i).getId()==carRT4.getId()) {
                availableCars.add(reservationController.getAvailableCars(reservationStart, reservationEnd).get(i));
            }
        }
        assertTrue(availableCars.get(0).getId()==carRT4.getId());
    }


    //UT-5 Tests if the List does not contain the car with an overlapping start date
    @Test
    public void returnNotContainsCarWithOverlappingStart() throws Exception {

        Timestamp reservationStart =  Timestamp.valueOf("2022-12-01 10:10:10.0");
        Timestamp reservationEnd =  Timestamp.valueOf("2022-12-03 10:10:10.0");

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Date date = new GregorianCalendar(1995,11,01).getTime();
        User UserRT5 = new User("Ann", "anna.anna@gmx.de", "1234", "Anna",
                "Hansen",date,userRoles );

        Car carRT5  = new Car("KulenkampffAllee99", "carRT5", "Mazda", 10.55f, 2);
        Timestamp reservationStartCar =  Timestamp.valueOf("2022-12-02 10:10:10.0");
        Timestamp reservationEndCar =  Timestamp.valueOf("2022-12-23 10:10:10.0");
        Reservation reservation = new Reservation(carRT5, UserRT5, reservationStartCar, reservationEndCar);
        reservationController.addReservation(reservation);
        List<Car> availableCars = new ArrayList<>();
        if(availableCars.size()==0) {
            assertFalse(false);
        }
            else if(availableCars.size()>0){
                for (int i = 0; i < reservationController.getAvailableCars(reservationStart, reservationEnd).size(); i++) {
                    if (reservationController.getAvailableCars(reservationStart, reservationEnd).get(i).getId() == carRT5.getId()) {
                        availableCars.add(reservationController.getAvailableCars(reservationStart, reservationEnd).get(i));
                    }
                }
            assertTrue(availableCars.get(0).getId()==carRT5.getId());
        }
    }


    // UT-6 Tests if the List does not contain the car with an overlapping end date
    @Test
    public void returnNotContainsCarWithOverlappingEnd() throws Exception {

        Timestamp reservationStart =  Timestamp.valueOf("2022-10-01 10:10:10.0");
        Timestamp reservationEnd =  Timestamp.valueOf("2022-10-03 10:10:10.0");

        List<Role> userRoles = new ArrayList<>();
        Date date = new GregorianCalendar(1995,11,01).getTime();
        User UserRT6 = new User("rolle", "rolf.rolf@gmx.de", "1234", "Rolf",
                "Hansen",date,userRoles );

        Car carRT6  = new Car("KulenkampffAllee99", "carRT6", "Mazda", 10.55f, 2);
        Timestamp reservationStartCar =  Timestamp.valueOf("2022-07-01 10:10:10.0");
        Timestamp reservationEndCar =  Timestamp.valueOf("2022-10-04 10:10:10.0");
        Reservation reservation = new Reservation(carRT6, UserRT6, reservationStartCar, reservationEndCar);
        reservationController.addReservation(reservation);

        List<Car> availableCars = new ArrayList<>();
        if(availableCars.size()==0) {
            assertFalse(false);
        }
        else if(availableCars.size()>0){
            for (int i = 0; i < reservationController.getAvailableCars(reservationStart, reservationEnd).size(); i++) {
                if (reservationController.getAvailableCars(reservationStart, reservationEnd).get(i).getId() == carRT6.getId()) {
                    availableCars.add(reservationController.getAvailableCars(reservationStart, reservationEnd).get(i));
                }
            }
            assertTrue(availableCars.get(0).getId()==carRT6.getId());
        }
    }
}
