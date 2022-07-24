package com.csn.carSharingNow.controller;


import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CarRepository carRepository;
    

    public List<Reservation>  getAllReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationList;
    }
    
    public List<Reservation>  getAllReservationsForUser(long id) {
        List<Optional<Reservation>> reservationList = reservationRepository.findByuser_id(id); 
        List<Reservation> myReservationList = new ArrayList<>();
        
        for (Optional<Reservation> reservierung : reservationList) {
			if (reservierung.isPresent()) {			
				myReservationList.add(reservierung.get());
			}
		}
        
        return myReservationList;
    }
    public void addReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
    public void addReservation(Car car, User  user, Timestamp reservationStart, Timestamp reservationEnd) {
        Reservation newReservation = new Reservation(car, user, reservationStart, reservationEnd);       
        reservationRepository.save(newReservation);
    }

    @Transactional
    public void cancelReservation(int id) {
        reservationRepository.removeById(id);
    }

    public Reservation findReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public List<Car> getAvailableCars(Timestamp reservationStart, Timestamp reservationEnd) {

        List<Car> availableCarList = carRepository.findAll();
        List<Reservation> allReservationsList = reservationRepository.findAll();
        List<Reservation> reservationsWithinSelectedTimeframeList = new ArrayList<>(); 
        
        for (Reservation reservation : allReservationsList) {
        	if (reservationEnd.after(reservation.getReservationStart())&&reservationEnd.before(reservation.getReservationEnd())) {
        		reservationsWithinSelectedTimeframeList.add(reservation);
            }else if (reservationStart.after(reservation.getReservationStart()) && reservationStart.before(reservation.getReservationEnd())) {           
            	reservationsWithinSelectedTimeframeList.add(reservation);
            }else if (reservationStart.before(reservation.getReservationStart()) && reservationEnd.after(reservation.getReservationEnd())) {
            	reservationsWithinSelectedTimeframeList.add(reservation);
            } 
        }
        
        for(Reservation res : reservationsWithinSelectedTimeframeList) {
        	for(Car car : availableCarList) {
        		if(car.getId() ==res.getCar().getId()) {
        			availableCarList.remove(car);
        		}
        	}
        }
        
        
        return availableCarList;
    }

}
