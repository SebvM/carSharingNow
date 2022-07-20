package com.csn.carSharingNow.models;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.csn.carSharingNow.security.SecurityConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * User Modell
 * 
 * @author Sebastian von Minden
 *
 */

@Entity
@Table(name = "user")
public class User {
	

	@Transient
	PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
	
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
	public Set<Role> roles;
    
	public User() {
	}
	
	public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password =  passwordEncoder.encode(user.getPassword());
        this.dateOfBirth = user.getDateOfBirth();
        this.roles = user.getRoles();
    }
	
	public User(String username, String email, String password, String firstname, String lastname, Date dateOfBirth, Set<Role> roles ) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = passwordEncoder.encode(password);
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
