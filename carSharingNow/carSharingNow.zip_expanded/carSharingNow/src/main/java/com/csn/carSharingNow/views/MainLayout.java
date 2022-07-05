package com.csn.carSharingNow.views;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.security.SecurityService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;




@Route("")
@PermitAll
public class MainLayout extends AppLayout {

	 MenuBar menuBar = new MenuBar();
	 Text selected = new Text("");
	 
	 ComponentEventListener<ClickEvent<MenuItem>> listener = e -> selected.setText(e.getSource().getText());

	 ComponentEventListener<ClickEvent<MenuItem>> logOutListener = null;
	 ComponentEventListener<ClickEvent<MenuItem>> userAccountListener = null;	 
	 ComponentEventListener<ClickEvent<MenuItem>> reservationsListener = null;
	 ComponentEventListener<ClickEvent<MenuItem>> homeListener = null;
	 
	 
	 
	 Div message = new Div(new Text("Geklickter Button: "), selected);	 
	 String username = "";
	    public MainLayout(@Autowired SecurityService securityService) {
	    	
	    	System.out.println("Mainlayout");
	    	
	    	H1 ha = new H1("carSharingNow");
			StreamResource imageResource = new StreamResource("cSn-Logo-removebg-preview.png",
				    () -> getClass().getResourceAsStream("/images/cSn-Logo-removebg-preview.png"));

			Image logoImage = new Image(imageResource, "carSharing Now");
			H1 logo = new H1(logoImage);

	        //nur wenn auch ein Benutzer angemeldet ist (einfach nur aus sicherheit)
	        if (securityService.get().isPresent()) {
	        	
	        	//Benutzer Account Menüpunkt
	        	username = securityService.get().get().getUsername();
	        	userAccountListener = e->  selected.setText(e.getSource().getText());
	        	menuBar.addItem(username, logOutListener);
	        	
	        	
	        	//Logout Menüpunkt
	        	logOutListener =  e ->  securityService.logout();      
	            menuBar.addItem("Logout", logOutListener);
	            
	            
	        } else {
	        	menuBar.getUI().ifPresent(ui -> ui.navigate("login"));
	        }
	        
	        VerticalLayout view = new VerticalLayout();
	        view.addComponentAsFirst(menuBar);
	        setContent(view);

	    }
}