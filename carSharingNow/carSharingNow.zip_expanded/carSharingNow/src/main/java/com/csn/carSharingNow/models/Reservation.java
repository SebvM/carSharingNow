package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.repositories.CarRepository;

import java.util.Date;
import java.util.Optional;


@Entity
@Getter @Setter
@Table(name="reservation")
public class Reservation {
	
	
    public Reservation(){

    }
    public Reservation(int carID, long userID, Date reservationStart, Date reservationEnd){
        this.carID = carID;
        this.userID = userID;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private boolean canceled=false;
    private int carID;
    private String carName;
    private long userID;
    private Date reservationStart;
    private Date reservationEnd;

    
	@Override
	public String toString() {
	  return "Reservation{" + "id=" + this.id + ", canceled='" + this.canceled + '\'' + ", carID='" 
			  + this.carID + '\'' + ", userID='" + this.userID + '\'' + ", reservationStart='" + this.reservationStart +'\'' + ", reservationEnd='" + this.reservationEnd + '\'' + '}';
	}

}
