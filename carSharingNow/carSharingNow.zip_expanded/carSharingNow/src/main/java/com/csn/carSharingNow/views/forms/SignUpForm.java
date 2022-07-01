package com.csn.carSharingNow.views.forms;

import java.util.stream.Stream;

import javax.annotation.security.PermitAll;

import com.csn.carSharingNow.models.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Registrieren | car Sharing Now")
public class SignUpForm extends FormLayout  {


	   private H3 title;
	   
	   private TextField username;
	   private TextField firstname;
	   private TextField lastname;
	   private DatePicker  dateOfBirth ;

	   private EmailField email;

	   private PasswordField password;
	   private PasswordField passwordConfirm;

	   private Checkbox adminRole;

	   private Span errorMessageField;

	   private Button submitButton;
	   
	   private User user;
	   
	   
	   public SignUpForm() {	
		   addClassName("signUp-form");
	       title = new H3("Registriere dich!!!");
	       username = new TextField("Benutzername");
	       firstname = new TextField("Vorname");
	       lastname = new TextField("Nachname");
	       email = new EmailField("Email");
	       dateOfBirth = new DatePicker("Geburtsdatum");

	       adminRole = new Checkbox("Erstelle Admin Account");
	       adminRole.getStyle().set("margin-top", "10px");

	       password = new PasswordField("Passwort");
	       passwordConfirm = new PasswordField("Passwort bestätigen");
	       
	       
	       setRequiredIndicatorVisible( username, firstname, lastname, email, password,
	               passwordConfirm, dateOfBirth);

	       username.setErrorMessage("This field is required");
	       firstname.setErrorMessage("This field is required");
	       lastname.setErrorMessage("This field is required");
	       email.setErrorMessage("This field is required");
	       password.setErrorMessage("This field is required");
	       passwordConfirm.setErrorMessage("This field is required");
	       
	       errorMessageField = new Span();

	       submitButton = new Button("Registriere dich jetzt gleich :D");
	       submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	       
	       dateOfBirth.setRequired(true);
	       
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
	   
	   
	   public PasswordField getPasswordField() { return password; }

	   public PasswordField getPasswordConfirmField() { return passwordConfirm; }

	   public Span getErrorMessageField() { return errorMessageField; }

	   public Button getSubmitButton() { return submitButton; }
	   	   
	
	 
	   private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
	       Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	   }
	   
	   
	   //Events für das validieren der daten
	  
		

}

