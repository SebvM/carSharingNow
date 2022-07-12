package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.repositories.CarRepository;
import com.csn.carSharingNow.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;


@Entity
@Getter @Setter
@Table(name="reservation")
public class Reservation {
	
	@Transient
	UserRepository userRepository;
	@Transient
	CarRepository carRepository;
	
    public Reservation(){

    }
    @SuppressWarnings("deprecation")
	public Reservation(Long carID, Long userID, Date reservationStart, Date reservationEnd){
        this.car = carRepository.getById(carID);
        this.user = userRepository.getById(userID);
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private boolean canceled=false;   
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "carID")
    private Car car;
    private String carName;
    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private User user;
    private Date reservationStart;
    private Date reservationEnd;

    public String getcarName() {
		return car.getName();
	}


    
	@Override
	public String toString() {
	  return "Reservation{" + "id=" + this.id + ", canceled='" + this.canceled + '\'' + ", carID='" 
			  + this.car.getId() + '\'' + ", userID='" + this.user.getId() + '\'' + ", reservationStart='" + this.reservationStart +'\'' + ", reservationEnd='" + this.reservationEnd + '\'' + '}';
	}

}
