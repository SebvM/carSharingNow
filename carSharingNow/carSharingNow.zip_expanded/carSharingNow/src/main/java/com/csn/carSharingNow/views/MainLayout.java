package com.csn.carSharingNow.views;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;




@Route("")
@PermitAll
public class MainLayout extends AppLayout {

	 private SecurityService securityService;

	    public MainLayout(@Autowired SecurityService securityService) {
	        this.securityService = securityService;

	        H1 logo = new H1("carSharingNow");
	        Button currentUser = new Button(securityService.get().toString());
	        logo.addClassName("logo");
	        HorizontalLayout header = new HorizontalLayout();
	        if (securityService.get() != null) {
	            Button logoutButton = new Button("Logout", click ->
	                    securityService.logout());
	            logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
	            header.add(logo); 
	            header.setAlignItems(Alignment.END);
	            header.add(currentUser); 
	            header.add(logoutButton);
	            
	        } else {
	        	header.add(logo); 
	        }

	        // Other page components omitted.

	        addToNavbar(header);
	    }
}