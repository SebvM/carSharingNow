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
    
    //Findet alle Reservierungen für den User und fügt diese der Ergebnisliste hinzu, da die ergebnisse auch leer sein können.
    public List getAllReservationsForUser(long id) {
        List<Optional<Reservation>> reservationList = reservationRepository.findByuserId(id); 
        List<Reservation> myReservationList = new ArrayList<>();
        
        for (Optional<Reservation> reservierung : reservationList) {
			if (reservierung.isPresent()) {			
				myReservationList.add(reservierung.get());
			}
		}
        
        return myReservationList;
    }
    
    public void addReservation(Long carID, long userID, Date reservationStart, Date reservationEnd) {
        Reservation newReservation = new Reservation(carID, userID, reservationStart, reservationEnd);
        newReservation.setCarName(carRepository.findById(carID).get().getName());
        reservationRepository.save(newReservation);
    }

    public void cancelReservation(Long id) {
        reservationRepository.removeById(id);
    }

    public Optional<Reservation> findReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Car> getAvailableCars(Date reservationStart, Date reservationEnd) {

        List<Car> unavailableCarList = new ArrayList<>();
        List<Car> availableCarList = carRepository.findAll();
        List<Reservation> allReservationsList = reservationRepository.findAll();

        for (Reservation reservation : allReservationsList) {
            if ((reservationStart.after(reservation.getReservationEnd()))
                    && reservationEnd.before(reservationStart)) {
            	unavailableCarList.add(carRepository.findCarById(reservation.getCar().getId()));
            }
        }
        
        availableCarList.removeAll(unavailableCarList);
        
        return availableCarList;
    }

}
