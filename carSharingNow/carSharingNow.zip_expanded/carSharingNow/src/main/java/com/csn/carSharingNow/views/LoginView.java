package com.csn.carSharingNow.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login") 
@PageTitle("Login | car Sharing Now")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm(); 
	
	private Button signUpButton;
	public LoginView(){
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		login.setForgotPasswordButtonVisible(false);
		login.setAction("login"); 
		
		signUpButton = new Button("Registriere dich jetzt gleich :D");		
		signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		signUpButton.addClickListener(e -> signUpButton.getUI().ifPresent(ui -> ui.navigate("signUp")));
		
		add(new H1("CarSharingNow"), login, signUpButton);
		
	}

	
	 @Override
	    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
	        if (beforeEnterEvent.getLocation()
	            .getQueryParameters()
	            .getParameters()
	            .containsKey("error")) {
	            login.setError(true);
	        }
	    }

	  
}