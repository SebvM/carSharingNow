package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String name;
    private String carBrand;
    private float mileage;
    private int carSeats;


    private CarStationEnum carStationEnum;


    public Car(){
    }
    public Car(String carStationEnum, String name,String carBrand, float mileage, int carSeats) { //CarStation carStation
        this.carStationEnum= CarStationEnum.valueOf(carStationEnum);
        this.name = name;
        this.carBrand = carBrand;
        this.mileage = mileage;
        this.carSeats = carSeats;

    }

    @Override
    public String toString() {
        return this.name ;
    }

}
