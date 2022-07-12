package com.csn.carSharingNow.controller;


import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.ReservationRepository;
import com.csn.carSharingNow.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    CarController carController;
    @Autowired
    UserController userController;
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
			} else {
				myReservationList = null;
			}
		}
        
        return myReservationList;
    }
    
    public void addReservation(Long carID, Date reservationStart, Date reservationEnd) {
    	Long userID = userController.getCurrenUser().getId();
        Reservation newReservation = new Reservation(carID, userID, reservationStart, reservationEnd);
        newReservation.setCarName(carController.getCarByID(carID).getName());
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
        @SuppressWarnings("unchecked")
		List<Car> availableCarList = carController.getAllCars();
        List<Reservation> allReservationsList = reservationRepository.findAll();

        for (Reservation reservation : allReservationsList) {
            if ((reservationStart.after(reservation.getReservationEnd()))
                    && reservationEnd.before(reservationStart)) {
            	unavailableCarList.add(carController.getCarByID(reservation.getCar().getId()));
            }
        }
        
        availableCarList.removeAll(unavailableCarList);
        
        return availableCarList;
    }

}
