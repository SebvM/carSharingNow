package com.csn.carSharingNow.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name ="carStation")

public class CarStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )

    private int id;
    private String city;
    private String street;
    private String houseNumber;

    //@OneToOne(targetEntity = Car.class, cascade = CascadeType.ALL)
    //private Car car;

   /* @OneToMany(
            mappedBy = "carStation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Car> carList= new ArrayList<Car>();
*/
    CarStation(){
    }
    CarStation(String city, String street, String houseNumber){
    this.city=city;
    this.street=street;
    this.houseNumber=houseNumber;
    }


    /*GustavHeinemannBuergerhaus60 ("Bremen","Gustav Heinemann Buergerhaus","60"),
    HanoverscheStrasse12 ("Bremen","Hanoversche Strasse","12"),
    FlughafenAllee10 ("Bremen","Flughafenallee", "10"),
    KulenkampffAllee99 ("Bremen","Kulenkampffallee", "95");*/

    @Override
    public String toString() {
        return "CarStation{" + "id=" + this.id + '\'' + ", city='" + this.city + '\'' + ", street='" + this.street + '\'' + ", houseNumber='"
                + this.houseNumber + '}';
    }
}
