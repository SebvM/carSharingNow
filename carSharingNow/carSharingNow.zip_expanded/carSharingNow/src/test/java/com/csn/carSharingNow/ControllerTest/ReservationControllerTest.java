package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
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
        Car carRT1  = new Car("KulenkampffAllee99", "carRT1", "VW", 10.55f, 4);
        User UserRT1 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",new Date (1999,11,01),userRoles );
        Date reservationStart = new Date(2022,06,01);
        Date reservationEnd = new Date(2022,06,03);
        reservationController.addReservation(carRT1,UserRT1,reservationStart,reservationEnd);
        Reservation reservationRT1 =reservationController.getAllReservations().get(reservationController.getAllReservations().size()-1);
        assertTrue(reservationController.getAllReservations().contains(reservationRT1));
    }

    //UT-2 Tests if all reervations for one user were listed
    @Test
    public void returnContainsAllReservationsForUser() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Car carRT1  = new Car("KulenkampffAllee99", "carRT1", "VW", 10.55f, 4);
        User UserRT2 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",new Date (1999,11,01),userRoles );
        Date reservationStart = new Date(2022,06,01);
        Date reservationEnd = new Date(2022,06,03);
        reservationController.addReservation(carRT1,UserRT2,reservationStart,reservationEnd);
        Date reservationStartSecond = new Date(2022,07,21);
        Date reservationEndSecond = new Date(2022,07,23);
        reservationController.addReservation(carRT1,UserRT2,reservationStartSecond,reservationEndSecond);

        List<Reservation> reservationList = new ArrayList<>();
        for(Reservation reservation : reservationController.getAllReservations()){
            if(reservation.getUser().equals(UserRT2)){
                reservationList.add(reservation);
            }
        }
        for(int i=0; i<reservationList.size(); i++){
            assertEquals(reservationList.get(i), reservationController.getAllReservationsForUser(UserRT2.getId()).get(i));
        }
    }

    //UT-3 Tests if a reservation is cancelable and if only the canceled reservation gets removed
    @Test
    public void returnListDoesNotContainsCanceled() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Car carRT2  = new Car("KulenkampffAllee99", "carRT2", "Ford", 10.55f, 2);
        User UserRT3 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",new Date (1999,11,01),userRoles );
        Date reservationStart = new Date(2022,06,01);
        Date reservationEnd = new Date(2022,06,03);
        reservationController.addReservation(carRT2,UserRT3,reservationStart,reservationEnd);
        Reservation reservation1 = reservationController.getAllReservations().get(0);
        Date reservationStartSecond = new Date(2022,07,21);
        Date reservationEndSecond = new Date(2022,07,23);
        Reservation reservation2 = reservationController.getAllReservations().get(1);
        reservationController.addReservation(carRT2,UserRT3,reservationStartSecond,reservationEndSecond);

        reservationController.cancelReservation(reservation2.getId());
        assertTrue((reservationController.getAllReservations().contains(reservation1))
        && (! reservationController.getAllReservations().contains(reservation2)));
    }

    //UT-4 Tests if a free Cars gets listed
    @Test
    public void returnListContainsAllAvailableCars() throws Exception {

        Date reservationStart = new Date(2022,06,01);
        Date reservationEnd = new Date(2022,06,03);

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        User UserRT4 = new User("Time", "time@gmx.de", "1234", "Tim",
                "Hansen",new Date (1999,11,01),userRoles );
        Car carRT4  = new Car("KulenkampffAllee99", "carRT3", "Mazda", 10.55f, 2);
        Date reservationStartCarRT4 = new Date(2022,06,22);
        Date reservationEndCarRT4 = new Date(2022,06,23);
        reservationController.addReservation(carRT4,UserRT4,reservationStartCarRT4,reservationEndCarRT4);

        List<Car> allReservations = reservationController.getAvailableCars(reservationStart, reservationEnd);
        assertTrue(allReservations.contains(carRT4));
    }

    //UT-5 Tests if the List does not contain the car with an overlapping start date
    @Test
    public void returnNotContainsCarWithOverlappingStart() throws Exception {

        Date reservationStart = new Date(2022,07,01);
        Date reservationEnd = new Date(2022,07,03);

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        User UserRT5 = new User("Ann", "anna@gmx.de", "1234", "Anna",
                "Hansen",new Date (1999,11,01),userRoles );

        Car carRT5  = new Car("KulenkampffAllee99", "carRT5", "Mazda", 10.55f, 2);
        Date reservationStartCarRT5 = new Date(2022,07,02);
        Date reservationEndCarRT5 = new Date(2022,07,20);
        reservationController.addReservation(carRT5,UserRT5,reservationStartCarRT5,reservationEndCarRT5);

        List<Car> allReservations = reservationController.getAvailableCars(reservationStart, reservationEnd);
        assertFalse(allReservations.contains(carRT5));
    }

    // UT-6 Tests if the List does not contain the car with an overlapping end date
    @Test
    public void returnNotContainsCarWithOverlappingEnd() throws Exception {

        Date reservationStart = new Date(2022,10,03);
        Date reservationEnd = new Date(2022,10,06);

        List<Role> userRoles = new ArrayList<>();
        User UserRT6 = new User("Stef", "anna@gmx.de", "1234", "Steffanie",
                "Hansen",new Date (1999,11,01),userRoles );

        Car carRT6  = new Car("KulenkampffAllee99", "carRT5", "Mazda", 10.55f, 2);
        Date reservationStartCarRT6 = new Date(2022,07,01);
        Date reservationEndCarRT6 = new Date(2022,10,04);
        reservationController.addReservation(carRT6,UserRT6,reservationStartCarRT6,reservationEndCarRT6);

        List<Car> allReservations = reservationController.getAvailableCars(reservationStart, reservationEnd);
        assertFalse(allReservations.contains(carRT6));
    }
}
