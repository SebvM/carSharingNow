package com.csn.carSharingNow.controller;


import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CarRepository carRepository;

    public ReservationController() {

    }

    public List getAllReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationList;
    }

    public void addReservation(int carID, Date reservationStart, Date reservationEnd) {
        Reservation newReservation = new Reservation(carID, reservationStart, reservationEnd);
        reservationRepository.save(newReservation);
    }

    public void cancelReservation(int id) {
        reservationRepository.removeByReservationID(id);
    }

    public Reservation findReservationById(int id) {
        return reservationRepository.findByReservationID(id);
    }

    public List getAvailableCars(Date reservationStart, Date reservationEnd) {

        List<Car> availableCarList = new ArrayList<>();
        List<Reservation> allReservationsList = reservationRepository.findAll();

        for (Reservation reservation : allReservationsList) {
            if ((reservationStart.after(reservation.getReservationEnd()))
                    && reservationEnd.before(reservationStart)) {
                availableCarList.add(carRepository.findCarById(reservation.getCarID()));
            }
        }
        return availableCarList;
    }

}
