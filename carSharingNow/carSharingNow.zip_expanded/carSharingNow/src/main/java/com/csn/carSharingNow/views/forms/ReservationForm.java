package com.csn.carSharingNow.views.forms;

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

import java.util.List;

public class ReservationForm extends FormLayout {   
	
  DateTimePicker startTime = new DateTimePicker("Start der Reservierung");
  DateTimePicker endTime = new DateTimePicker("Ende der Reservierung");
  
  ComboBox<Car> car = new ComboBox<Car>();
  
  Button delete = new Button("Löschen");
  Button close = new Button("Zurück");

  public ReservationForm(Reservation reservation) {
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


  
}