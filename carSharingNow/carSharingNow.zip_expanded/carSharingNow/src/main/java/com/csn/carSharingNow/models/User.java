package com.csn.carSharingNow.models;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
public class User {
	
	@Transient
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
    @JsonIgnore
    @Size(max = 120)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumns({	
    		@JoinColumn(name="id", referencedColumnName="ID"),
            @JoinColumn(name="rolename", referencedColumnName="role") 
    		})
    private Set<Role> roles;
    
	public User() {
	}

	public User(String username, String email, String password, String firstname, String lastname, Date dateOfBirth, Set<Role> roles ) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = passwordEncoder.encode(password).toString();
		System.out.println("Klartext:" + password + "    Encoded:" + this.password );
		this.roles = roles;
	}
	
	@Override
	public String toString() {
	  return "User{" + "id=" + this.id + ", username='" + this.username + '\'' + ", firstname='" 
			  + this.firstname + '\'' + ", lastname='" + this.lastname + '\'' + ", dateOfBirth='" + this.dateOfBirth +'\'' + ", email='" + this.email + '\'' + '}';
	}


	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	    
	    
}
