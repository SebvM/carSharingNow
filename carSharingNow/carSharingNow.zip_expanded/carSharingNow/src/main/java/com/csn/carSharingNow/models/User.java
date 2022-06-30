package com.csn.carSharingNow.models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {
	
	PasswordEncoder passwordEncoder;
	
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
    private Collection<Role> authorities;
	public User() {
	}

	public User(String username, String email, String password, String firstname, String lastname, Date dateOfBirth, Collection<Role> authorities ) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = passwordEncoder.encode(password);
		System.out.println("Klartext:" + password + "    Encoded:" + this.password );
		this.authorities = authorities;
	}
	
	@Override
	public String toString() {
	  return "User{" + "id=" + this.id + ", username='" + this.username + '\'' + ", firstname='" 
			  + this.firstname + '\'' + ", lastname='" + this.lastname + '\'' + ", dateOfBirth='" + this.dateOfBirth +'\'' + ", email='" + this.email + '\'' + '}';
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
