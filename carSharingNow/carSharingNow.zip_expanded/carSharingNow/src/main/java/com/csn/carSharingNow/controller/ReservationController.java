package com.csn.carSharingNow.controller;


import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
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
    
    public List getAllReservationsForUser(long id) {
        List<Optional<Reservation>> reservationList = reservationRepository.findByUserID(id); 
        List<Reservation> myReservationList = new ArrayList<>();
        
        for (Optional<Reservation> reservierung : reservationList) {
			if (reservierung.isPresent()) {
				if(reservierung.get().getCarName().isBlank() || reservierung.get().getCarName().isEmpty()) {
					reservierung.get().setcarName(carRepository.findById(reservierung.get().getCarID()).get().getName());
				}


				myReservationList.add(reservierung.get());
			}
		}
        
        return myReservationList;
    }
    
    public void addReservation(int carID, long userID, Date reservationStart, Date reservationEnd) {
        Reservation newReservation = new Reservation(carID, userID, reservationStart, reservationEnd);
        newReservation.setcarName(carRepository.findById(carID).get().getName());
        reservationRepository.save(newReservation);
    }

    public void cancelReservation(int id) {
        reservationRepository.removeById(id);
    }

    public Reservation findReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public List<Car> getAvailableCars(Date reservationStart, Date reservationEnd) {

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
