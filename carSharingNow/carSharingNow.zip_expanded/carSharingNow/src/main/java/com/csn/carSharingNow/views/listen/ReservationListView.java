package com.csn.carSharingNow.views.listen;

import java.util.Collections;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.security.SecurityService;
import com.csn.carSharingNow.views.MainLayout;
import com.csn.carSharingNow.views.forms.ReservationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
/**
 * View die eine Liste in einem Grid anzeigt 
 * und ein Eigabefeld für neue elemente für die Liste
 * 
 * @author Sebastian von Minden
 *
 */

@Route(value = "myReservations", layout=MainLayout.class)
@PermitAll
@PageTitle("Reservierungen | car Sharing Now")
public class ReservationListView extends VerticalLayout implements AfterNavigationObserver { 
	@Autowired
	ReservationController reservationController;
	@Autowired
	SecurityService securityService;
	
    Grid<Reservation> grid = new Grid<>(Reservation.class); 
    
    ReservationForm resForm;
    
    public ReservationListView() {
        addClassName("reservation-list-view");
        setSizeFull();
        configureGrid(); 
        configureForm();
        
        add(getToolbar(), getContent()); 
    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, resForm);
        content.setFlexGrow(2, grid); 
        content.setFlexGrow(1, resForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureGrid() {
        grid.addClassNames("reservation-grid");
        grid.setSizeFull();
        grid.setColumns("carName","reservationStart", "reservationEnd");       
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
    }
    private void configureForm() {
    	resForm = new ReservationForm(null); 
        resForm.setWidth("25em");
    }
    
    private HorizontalLayout getToolbar() {

        Button addContactButton = new Button("Reservierung hinzufügen");

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton); 
        toolbar.addClassName("toolbar");
        return toolbar;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		this.grid.setItems(reservationController.getAllReservationsForUser(securityService.get().get().getId()));
		
	}
}