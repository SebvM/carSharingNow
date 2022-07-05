package com.csn.carSharingNow.views.forms;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.security.SecurityService;
import com.csn.carSharingNow.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Form um die Accountdaten zu ändern
 * 
 * @author Sebastian von Minden
 *
 */

public class UserDataForm extends FormLayout  {
	   private H3 title;
	   
	   private TextField username;
	   private TextField firstname;
	   private TextField lastname;
	   private DatePicker dateOfBirth;
	   

	   private EmailField email;

	   private PasswordField password;
	   private PasswordField passwordConfirm;

	   private Checkbox adminRole;

	   private Span errorMessageField;

	   private Button submitButton;
	   
	   @Autowired 
	   SecurityService securityService;
	   
	   public UserDataForm(	) {	

		 
		
		   addClassName("signUp-form");
	       title = new H3("Nutzerdaten anpassen");
	       username = new TextField("Benutzername");
	       firstname = new TextField("Vorname");
	       lastname = new TextField("Nachname");
	       email = new EmailField("Email");
	       dateOfBirth = new DatePicker("Geburtsdatum");
	       
	       adminRole = new Checkbox("Erstelle Admin Account");
	       adminRole.getStyle().set("margin-top", "10px");

	       password = new PasswordField("Passwort");
	       passwordConfirm = new PasswordField("Passwort bestätigen");
	       
	       
	       username.setEnabled(false);
	       email.setEnabled(false);
	       
		   
		   
	       setRequiredIndicatorVisible( username, firstname, lastname, email, password,
	               passwordConfirm, dateOfBirth);

	       firstname.setErrorMessage("Vorname ist notwendig");
	       lastname.setErrorMessage("Nachname ist notwendig");
	       password.setErrorMessage("Ohne Passwort kannst du dich nicht einloggen");
	       passwordConfirm.setErrorMessage("Bitte Password bestätigen");
	       dateOfBirth.setErrorMessage("Ohne Geburtsdatum keine registrierung");	       
	       
	       errorMessageField = new Span();

	       submitButton = new Button("Bestätigen");
	       submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	       
	       dateOfBirth.setRequired(true);
	       LocalDate defaultdate = LocalDate.of(1978, 11, 19);;
	       dateOfBirth.setValue(defaultdate);
	       add(title, username, firstname, lastname, dateOfBirth, email, password,
	               passwordConfirm, adminRole, errorMessageField,
	               submitButton);

	       // Max width fürs Form
	       setMaxWidth("500px");

	   
	       setResponsiveSteps(
	               new ResponsiveStep("150px", 1, ResponsiveStep.LabelsPosition.TOP),
	               new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

	       //Achtung immer auf voller width
	       setColspan(title, 2);
	       setColspan(email, 2);
	       setColspan(errorMessageField, 2);
	       setColspan(submitButton, 2);
	       
	       
	       
	   }
	   
	   @PostConstruct
	   public void init() {

	       User user = securityService.get().get();
	       if(user != null)
			   this.setUserData(user);
	       
	   }
	   
	   public PasswordField getPasswordField() { return password; }

	   public PasswordField getPasswordConfirmField() { return passwordConfirm; }

	   public Span getErrorMessageField() { return errorMessageField; }

	   public Button getSubmitButton() { return submitButton; }
	   	   
	   public TextField getUsernameField() { return username; }
	   public TextField getFirstnameField() { return firstname; }
	   public TextField getLastnameField() { return lastname; }
	   public DatePicker getDateOfBirthField() { return dateOfBirth; }
	   public EmailField getEmailField() { return email; }
	   public Checkbox getAdminRole() { return adminRole; }


	   public void setPasswordField(String password) { this.password.setValue(password); }
	   public void setPasswordConfirmField(String passwordConfirm) { this.passwordConfirm.setValue(passwordConfirm); }
	   public void setUsernameField(String username) { this.username.setValue(username); }
	   public void setFirstnameField(String firstname) { this.firstname.setValue(firstname); }
	   public void setLastnameField(String lastname) { this.lastname.setValue(lastname); }
	   public void setDateOfBirthField(LocalDate dateOfBirth) { this.dateOfBirth.setValue(dateOfBirth); }
	   public void setEmailField(String email) { this.email.setValue(email); }
	 
	   private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
	       Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	   }

	   private void setUserData(User user) {
			if(user != null) {
				System.out.println(user.toString());
				this.setUsernameField( user.getUsername()); 
				this.setFirstnameField( user.getFirstname()); 
				this.setLastnameField(user.getLastname() ); 
				this.setDateOfBirthField( user.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); 
				this.setEmailField(user.getEmail() );
			}
		}
		
	   public Date getDatepickerDate() {
		   Date selectedDate = null; 
			selectedDate = Date.from(this.getDateOfBirthField().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());	
		   return selectedDate;
	   }

}
