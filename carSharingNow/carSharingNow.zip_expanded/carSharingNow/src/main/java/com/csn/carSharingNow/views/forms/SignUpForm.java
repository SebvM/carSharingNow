package com.csn.carSharingNow.views.forms;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;


import com.csn.carSharingNow.models.Role;
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
import com.vaadin.flow.router.PageTitle;

/**
 * Einfache Form die den Aufbau der Registrierungsseite beschreibt und erzeugt
 * 
 * @author Sebastian von Minden
 *
 */


@SuppressWarnings("serial")
@PageTitle("Registrieren | car Sharing Now")
public class SignUpForm extends FormLayout  {


	   private H3 title;
	   
	   private TextField username;
	   private TextField firstname;
	   private TextField lastname;
	   private DatePicker dateOfBirth;
	   

	   private EmailField email;

	   private PasswordField password;
	   private PasswordField passwordConfirm;


	   private Span errorMessageField;

	   private Button submitButton;
	   private static final Locale deLocale = new Locale("de", "DE");
	   
	   
	   public SignUpForm() {	
		   addClassName("signUp-form");
	       title = new H3("Registriere dich!!!");
	       username = new TextField("Benutzername");
	       firstname = new TextField("Vorname");
	       lastname = new TextField("Nachname");
	       email = new EmailField("Email");
	       dateOfBirth = new DatePicker("Geburtsdatum");


	       password = new PasswordField("Passwort");
	       passwordConfirm = new PasswordField("Passwort bestätigen");
	       
	       
	       setRequiredIndicatorVisible( username, firstname, lastname, email, password,
	               passwordConfirm, dateOfBirth);

	       username.setErrorMessage("Es muss ein Benuztername angegeben werden.");
	       firstname.setErrorMessage("Vorname ist notwendig");
	       lastname.setErrorMessage("Nachname ist notwendig");
	       email.setErrorMessage("Email ist notwendig");
	       password.setErrorMessage("Ohne Passwort kannst du dich nicht einloggen");
	       passwordConfirm.setErrorMessage("Bitte Password bestätigen");
	       dateOfBirth.setErrorMessage("Ohne Geburtsdatum keine registrierung");	       
	       
	       errorMessageField = new Span();
	       submitButton = new Button("Registriere dich jetzt gleich :D");
	       submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	       
	       dateOfBirth.setRequired(true);
	       dateOfBirth.setLocale(deLocale);
	       LocalDate defaultdate = LocalDate.of(1978, 11, 19);;
	       dateOfBirth.setValue(defaultdate);
	       
	       
	       username.addFocusListener(e -> resetErrorMassageField());
	       email.addFocusListener(e -> resetErrorMassageField());
	       password.addFocusListener(e -> resetErrorMassageField());
	       passwordConfirm.addFocusListener(e -> resetErrorMassageField());
	       
	       add(title, username, firstname, lastname, dateOfBirth, email, password,
	               passwordConfirm, errorMessageField,
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
	   
	   
	   public PasswordField getPasswordField() { return password; }

	   public PasswordField getPasswordConfirmField() { return passwordConfirm; }

	   public Span getErrorMessageField() { return errorMessageField; }

	   public Button getSubmitButton() { return submitButton; }
	   	   
	   public TextField getUsernameField() { return username; }
	   public TextField getFirstnameField() { return firstname; }
	   public TextField getLastnameField() { return lastname; }
	   public DatePicker getDateOfBirthField() { return dateOfBirth; }
	   public EmailField getEmailField() { return email; }



	 
	   private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
	       Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	   }

	   public List<Role> userRoles(){
		   List<Role> roles = new ArrayList<>();
		   roles.add(Role.USER);
		   
		   return roles;
	   }
		
	   public Date getDatepickerDate() {
		   Date selectedDate = null; 
			selectedDate = Date.from(this.getDateOfBirthField().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());	
		   return selectedDate;
	   }
	   
	   private void resetErrorMassageField() {
		   this.errorMessageField.setVisible(false);
		   this.errorMessageField.setText("Etwas ist schief gelaufen :(");
	   }
}

