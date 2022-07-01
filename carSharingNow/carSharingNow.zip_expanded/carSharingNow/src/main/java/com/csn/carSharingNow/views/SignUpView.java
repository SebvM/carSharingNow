package com.csn.carSharingNow.views;

import javax.annotation.security.PermitAll;

import com.csn.carSharingNow.views.forms.SignUpForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("signUp") 
@AnonymousAllowed
@PageTitle("Sign Up | car Sharing Now")
public class SignUpView extends VerticalLayout {
	private final SignUpForm signUp = new SignUpForm(); 

	private Button backToLogin;
	public SignUpView(){		
		addClassName("signUp-view");
		setSizeUndefined(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER, signUp);
		setPadding(true);
		backToLogin = new Button("zurück zum login");		
		backToLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		backToLogin.addClickListener(e -> backToLogin.getUI().ifPresent(ui -> ui.navigate("login")));
		
		add(new HorizontalLayout(new H1("CarSharingNow"), signUp, backToLogin));

	}
}
