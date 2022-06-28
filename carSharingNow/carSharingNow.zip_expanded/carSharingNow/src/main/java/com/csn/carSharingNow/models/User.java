package com.csn.carSharingNow.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    
    @Column(unique = true)
    private String email;
    private String password;
    
	public User() {
	}

	public User(String username, String email, String password, String firstname, String lastname, Date dateOfBirth) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public String toString() {
	  return "User{" + "id=" + this.id + ", username='" + this.username + '\'' + ", firstname='" 
			  + this.firstname + '\'' + ", lastname='" + this.lastname + '\'' + ", dateOfBirth='" + this.dateOfBirth +'\'' + ", email='" + this.email + '\'' + '}';
	}
}
