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
    @TableGenerator(name = "tabgenerator", allocationSize = 1,initialValue= -1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tabgenerator" )
    private int id;
    private String name;
    private String carBrand;
    private float mileage;
    private int carSeats;


    private CarStationEnum carStationEnum;


    public Car(){
    }
    public Car(String carStationEnum, String name,String carBrand, float mileage, int carSeats) {
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
