package com.csn.carSharingNow.models;

import lombok.Getter;

@Getter
public enum CarStationEnum {

    GustavHeinemannBuergerhaus60("Bremen", "Gustav Heinemann Buergerhaus", "60"),
    HanoverscheStrasse12("Bremen", "Hanoversche Strasse", "12"),
    FlughafenAllee10("Bremen", "Flughafenallee", "10"),
    KulenkampffAllee99("Bremen", "Kulenkampffallee", "95");

    private String city;
    private String street;
    private String houseNumber;


    private CarStationEnum(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;

    }
}
