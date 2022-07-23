package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReservationControllerTest {

    @Autowired
    ReservationController reservationController;
    @Autowired
    ReservationRepository reservationRepository;

    //
    @Test
    public void returnContainsAddedReservation() throws Exception {
        Car carRT1  = new Car("KulenkampffAllee99", "carRT1", "VW", 10.55f, 4);
        User UserRT1 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",new Date (2022,11,01), );
        Date reservationStart = new Date(2022,06,01);
        Date reservationEnd = new Date(2022,06,03);
        Reservation reservationRT1 = new Reservation(carRT1,UserRT1,reservationStart,reservationEnd);
        assertTrue(reservationController.getAllReservations().contains(reservationRT1));
    }

}
