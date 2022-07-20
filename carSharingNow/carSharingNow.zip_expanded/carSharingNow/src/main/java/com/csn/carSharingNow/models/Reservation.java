package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.repositories.CarRepository;

import java.util.Date;
import java.util.Optional;
/**
 * Reservation Modell
 * 
 * @author Sebastian von Minden
 *
 */

@Entity
@Getter @Setter
@Table(name="reservation")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    
    private boolean canceled=false;
    
    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;    
    
    @ManyToOne
    @JoinColumn(name = "car_ID")
    private Car car;
    private Date reservationStart;
    private Date reservationEnd;
    
    @Transient
    private String carName;    
    
    public Reservation() {
    }       
    public Reservation(Car car, User user, Date reservationStart, Date reservationEnd){
        this.car = car;
        this.user = user;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

   	public String getcarName() {
   		return car.getName();
   	}
	@Override
	public String toString() {
	  return "Reservation{" + "id=" + this.id + ", canceled='" + this.canceled + '\'' + ", carID='" 
			  + this.car.getId() + '\'' + ", userID='" + this.user.getId() + '\'' + ", reservationStart='" + this.reservationStart +'\'' + ", reservationEnd='" + this.reservationEnd + '\'' + '}';
	}

}
