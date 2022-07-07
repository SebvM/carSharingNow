package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


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
    
	public void setcarName(String carName) {
		this.carName = carName;
	}
    
    
}
