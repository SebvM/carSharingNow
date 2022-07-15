package com.csn.carSharingNow.views.forms;

import com.csn.carSharingNow.controller.ReservationController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.Reservation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
@Getter @Setter
public class ReservationForm extends FormLayout implements AfterNavigationObserver{   
	
  DateTimePicker startTime = new DateTimePicker("Start der Reservierung");
  DateTimePicker endTime = new DateTimePicker("Ende der Reservierung");
  
  ComboBox<Car> car = new ComboBox<Car>();
  List<Car> carList = new ArrayList<Car>();
  Button delete = new Button("Löschen");
  Button close = new Button("Zurück"); 
  Reservation selectedReservation;
  @Autowired
  ReservationController reservationController;
  
  public ReservationForm(Reservation selectedReservation) {
	this.selectedReservation = selectedReservation;  
    addClassName("rerservation-form"); 
    startTime.setReadOnly(true);
    endTime.setReadOnly(true);
    car.setReadOnly(true);
    
    add(startTime, 
    	endTime,
    	car,
        createButtonsLayout());    
  }

  private HorizontalLayout createButtonsLayout() {
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    close.addClickShortcut(Key.ESCAPE);
    return new HorizontalLayout(delete, close); 
  }
  
  DateTimePicker getstartTime() {
	return startTime;
  }  
  public void setstartTime(Date startTime) {
	this.startTime.setValue(startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }
  DateTimePicker getendTime() {
	return endTime;
  }
  public void setendTime(Date endTime) {
	this.endTime.setValue(endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }
  ComboBox<Car> getcarList(){
	return car;	  
  }  
  public void setcarList(List<Car> carList){
	this.car.setItems(carList);  
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
	  close.addClickListener(e -> this.setVisible(false));
	  delete.addClickListener(e ->  reservationController.cancelReservation(selectedReservation.getId()));	
  }
  
  
  
}