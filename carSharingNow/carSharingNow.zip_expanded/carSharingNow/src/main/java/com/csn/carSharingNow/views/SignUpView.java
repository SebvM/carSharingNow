package com.csn.carSharingNow.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import com.csn.carSharingNow.views.forms.SignUpForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Erzeugt die Registrierung als SignUpView
 * 
 * @author Sebastian von Minden
 *
 */

@SuppressWarnings("serial")
@Route("signUp") 
@AnonymousAllowed
@PageTitle("Sign Up | car Sharing Now")
public class SignUpView extends VerticalLayout {
	private final SignUpForm signUp = new SignUpForm(); 
	
	@Autowired
	UserController userController;
	@Autowired
	UserRepository userRepository;
	
	private Button backToLogin;
	public SignUpView(){		
		StreamResource imageResource = new StreamResource("cSn-Logo-removebg-preview.png",
			    () -> getClass().getResourceAsStream("/images/cSn-Logo-removebg-preview.png"));

		Image logoImage = new Image(imageResource, "carSharing Now");
		
		H1 header = new H1(logoImage);
		addClassName("signUp-view");
		setSizeUndefined(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER, signUp);
		setPadding(true);
		backToLogin = new Button("zurück zum login");		
		backToLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		backToLogin.addClickListener(e -> backToLogin.getUI().ifPresent(ui -> ui.navigate("login")));
		if(signUp.getUsernameField().isInvalid() || signUp.getFirstnameField().isInvalid() || signUp.getLastnameField().isInvalid() 
				||signUp.getDateOfBirthField().isInvalid() || signUp.getEmailField().isInvalid() || signUp.getPasswordField().isInvalid() 
				||signUp.getPasswordConfirmField().isInvalid()) {
			signUp.getSubmitButton().setEnabled(false);
		}		
		add(new VerticalLayout(header, signUp, backToLogin));	

		setHorizontalComponentAlignment(Alignment.CENTER, header);
		setHorizontalComponentAlignment(Alignment.CENTER, backToLogin);
		signUp.getSubmitButton().addClickListener(e ->  signUpButtonIsPressed());
		
		
	}
	
	public void signUpButtonIsPressed() {
		User requestedUserRegistration =  new User(signUp.getUsernameField().getValue(), signUp.getEmailField().getValue(), signUp.getPasswordField().getValue(),
				signUp.getFirstnameField().getValue(), signUp.getLastnameField().getValue(), signUp.getDatepickerDate(), signUp.userRoles());
		
		if(signUp.getPasswordField().isEmpty() || signUp.getPasswordConfirmField().isEmpty()) {
			signUp.getErrorMessageField().setVisible(true);
			signUp.getErrorMessageField().setText("Passwort darf nicht leer sein!");

		}else if(!signUp.getPasswordField().getValue().equals(signUp.getPasswordConfirmField().getValue())) {
			signUp.getErrorMessageField().setVisible(true);
			signUp.getErrorMessageField().setText("Passwörter müssen übereinstimmen!");

		}else if(userRepository.findByUsername(signUp.getUsernameField().getValue())== null) {
			if(requestedUserRegistration != null) {
				userController.addUser(requestedUserRegistration);
				signUp.getUI().ifPresent(ui -> ui.navigate("login"));
			} 
		}else if(userRepository.findByEmail(signUp.getEmailField().getValue()) == null){
			signUp.getErrorMessageField().setVisible(true);
			signUp.getErrorMessageField().setText(signUp.getEmailField().getErrorMessage());
		}else {
			signUp.getErrorMessageField().setVisible(true);
			signUp.getErrorMessageField().setText(signUp.getUsernameField().getErrorMessage());
		}
		
	}
}
