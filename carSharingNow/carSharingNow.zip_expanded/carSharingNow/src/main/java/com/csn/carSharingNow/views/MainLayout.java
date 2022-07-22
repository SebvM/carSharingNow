package com.csn.carSharingNow.views;


import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.security.SecurityService;
import com.csn.carSharingNow.views.listen.ReservationListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.orderedlayout.FlexComponent;



/**
 * Hauptseite erreicht über ../
 * 
 * @author Sebastian von Minden
 *
 */

@SuppressWarnings("serial")
@Route("")
@PermitAll
public class MainLayout extends AppLayout {
	 	String username = "";
	    public MainLayout(@Autowired SecurityService securityService) {
	 		if(securityService != null) {
		    	createHeader(securityService);
		        createDrawer(securityService);    	
	 		}	    	
	    }
	    
	    private void createHeader(SecurityService securityService) {
	        H1 logo = new H1("carSharing Now");
	        logo.addClassNames("text-l", "m-m");
	        
	        Button logout = new Button("Log out", e -> securityService.logout()); 
	        Button administration = new Button("Administration", e -> UI.getCurrent().navigate(AdministrationView.class));
        	administration.setVisible(false);
        	administration.setEnabled(false);
	    	
	        HorizontalLayout header = new HorizontalLayout(
	          new DrawerToggle(), 
	          logo,
	          administration,
	          logout
	        );
	        if(securityService.get().get().getRoles().contains(Role.ADMIN)) {
	        	administration.setVisible(true);
	        	administration.setEnabled(true);
	        }
	        
	        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
	        header.setWidth("100%");
	        header.expand(logo);
	        header.addClassNames("py-0", "px-m");

	        addToNavbar(header);	 
	    }
	    
	    private void createDrawer(SecurityService securityService) {
	    	if (securityService.get().isPresent() && securityService.get().get().getRoles().contains(Role.USER)) {	    		
	    		username = securityService.get().get().getUsername();
	    		//Benutzer Account Menüpunkt
	    		RouterLink accountDataLink = new RouterLink("Benutzerdaten: " + username, UserDataView.class ); 
	    		
	    		accountDataLink.setHighlightCondition(HighlightConditions.sameLocation()); 
	    		RouterLink reservationDataLink = new RouterLink("Meine Reservierungen", ReservationListView.class); 
	    		reservationDataLink.setHighlightCondition(HighlightConditions.sameLocation()); 

	    			addToDrawer(new VerticalLayout( 
		    				accountDataLink,
		    				reservationDataLink
		    				));
	    		
	    	
	    	}
	    }
	    
}