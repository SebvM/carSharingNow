package com.csn.carSharingNow.views.forms;

import com.csn.carSharingNow.models.Reservation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class ReservationForm extends FormLayout {   
	
  DateTimePicker startTime = new DateTimePicker();
  DateTimePicker endTime = new DateTimePicker();
  
  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ReservationForm(List<Reservation> reservations) {
    addClassName("contact-form"); 


    add(startTime, 
    	endTime,
        createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER); 
    close.addClickShortcut(Key.ESCAPE);

    return new HorizontalLayout(save, delete, close); 
  }
}