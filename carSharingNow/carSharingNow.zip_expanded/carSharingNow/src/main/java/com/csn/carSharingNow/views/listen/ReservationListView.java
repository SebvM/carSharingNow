package com.csn.carSharingNow.views.listen;

import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.csn.carSharingNow.security.SecurityService;
import com.csn.carSharingNow.views.MainLayout;
import com.csn.carSharingNow.views.forms.ReservationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
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

@SuppressWarnings("serial")
@Route(value = "myReservations", layout=MainLayout.class)
@PermitAll
@PageTitle("Reservierungen | car Sharing Now")
public class ReservationListView extends VerticalLayout implements AfterNavigationObserver { 
	@Autowired
	ReservationController reservationController;
	@Autowired
	CarController carController;
	@Autowired
	SecurityService securityService;
	
    Grid<Reservation> grid = new Grid<>(Reservation.class); 
    DateTimePicker startTime = new DateTimePicker("Start der Reservierung");
    DateTimePicker endTime = new DateTimePicker("Ende der Reservierung");     
    ComboBox<Car> carCombo = new ComboBox<Car>("Fahrzeug auswählen");
    Button addReservationButton = new Button("Reservierung hinzufügen");      
    Reservation selectedReservation;
    ReservationForm resForm;
    List<Car> availableCars = new ArrayList<Car>();
    
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
        resForm.setVisible(false);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureGrid() {
        grid.addClassNames("reservation-grid");
        grid.setSizeFull();
        grid.setColumns("carName","reservationStart", "reservationEnd");       
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }
    
    private void configureForm() {
    	resForm = new ReservationForm(null); 
        resForm.setWidth("25em");
    }
    
    private HorizontalLayout getToolbar() {
        endTime.setReadOnly(true);
        carCombo.setReadOnly(true);         
        startTime.setHelperText("Start muss vor Ende sein.");
        startTime.setStep(Duration.ofMinutes(15));
        endTime.setHelperText("Ende muss nach Start sein.");
        addReservationButton.setEnabled(false);
        HorizontalLayout toolbar = new HorizontalLayout(startTime, endTime, carCombo, addReservationButton); 
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.BASELINE);
        return toolbar;
    }

	
	private void gridUpdate() {
		grid.setItems(reservationController.getAllReservationsForUser(securityService.get().get().getId()));	

	}
	
	public void setReservationFormData(Reservation selectedReservation) {
		resForm.setSelectedReservation(selectedReservation);
		resForm.setVisible(true);
		
		Car car = selectedReservation.getCar();
		resForm.getCar().setValue(car.getName());
		resForm.setstartTime(selectedReservation.getReservationStart());
		resForm.setendTime(selectedReservation.getReservationEnd());		
	}
	
	private void startDateSelected() {
		if(!startTime.isEmpty()) {
			endTime.setReadOnly(false);
			endTime.setMin(startTime.getValue());
		}else {
			endTime.setValue(null);
		}
	}
	
	private void endDateSelected() {
		if(!endTime.isEmpty() && endTime.getValue().isAfter(startTime.getValue())) {
			carCombo.setReadOnly(false);
			availableCars = reservationController.getAvailableCars(Date.from(startTime.getValue().atZone(ZoneId.systemDefault()).toInstant()), Date.from(endTime.getValue().atZone(ZoneId.systemDefault()).toInstant()));
			carCombo.setItems(availableCars);
			availableCars.forEach(c -> System.out.println(c.toString()));
			System.out.println();
		}else {
			availableCars = new ArrayList<Car>();
			endTime.setValue(null);
		}
	}
	private void carComboSelected() {
		if(availableCars != null && carCombo.getValue() != null) {
			addReservationButton.setEnabled(true);
		} else {
			addReservationButton.setEnabled(false);
		}
	}
	private void addReservationButtonClicked() {
		Date startDate = Date.from(startTime.getValue().atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(endTime.getValue().atZone(ZoneId.systemDefault()).toInstant());
		Car selectedCar = carCombo.getValue();		
		reservationController.addReservation(selectedCar, securityService.get().get(), startDate, endDate);		
		availableCars = new ArrayList<Car>();
		carCombo.setItems(availableCars);
		carCombo.setReadOnly(true);
		endTime.setValue(null);
		endTime.setReadOnly(true);
		startTime.setValue(null);
		
		gridUpdate();
	}
	private void deleteReservation(Set<Reservation> set) {
		set.forEach(res -> reservationController.cancelReservation(res.getId()));
		
		resForm.setVisible(false);
		gridUpdate();
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		gridUpdate();
		grid.addSelectionListener(e -> e.getFirstSelectedItem().ifPresent(res -> setReservationFormData(selectedReservation = e.getFirstSelectedItem().get())) );
		startTime.addValueChangeListener(e -> startDateSelected());
		endTime.addValueChangeListener(e -> endDateSelected());
		carCombo.addValueChangeListener(e -> carComboSelected());
		addReservationButton.addClickListener(e -> addReservationButtonClicked());
		resForm.setReservationController(reservationController);
		resForm.getDelete().addClickListener(e-> deleteReservation(grid.getSelectedItems()));
	}	
}