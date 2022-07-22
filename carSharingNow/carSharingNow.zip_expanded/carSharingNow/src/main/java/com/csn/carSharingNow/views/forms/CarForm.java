package com.csn.carSharingNow.views.forms;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.models.CarStationEnum;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
public class CarForm extends FormLayout implements AfterNavigationObserver {	

		@Autowired
		CarController carController;
		
		private Car selectedCar;
		
		
		private TextField brand = new TextField("Marke");
		private TextField name= new TextField("Bezeichnung");
		private IntegerField seats = new IntegerField("Sitze");
		private IntegerField mileage = new IntegerField("Kilometerstand");
		private ComboBox<CarStationEnum> carStation = new ComboBox<CarStationEnum>("Station");
		private Button save = new Button("Speichern");
		private Button close = new Button("Zurück"); 
		  
		public CarForm(Car selectedCar) {
			this.selectedCar = selectedCar;  
			addClassName("user-form"); 

			add(brand,
				name,
				seats,
				mileage,
				carStation,
				createButtonsLayout());    
		}

		private void setValues(Car car) {
			if(car != null) {

				brand.setValue(car.getCarBrand());
				name.setValue(car.getName());
				seats.setValue(car.getCarSeats());
				mileage.setValue((int) car.getMileage());
				carStation.setValue(car.getCarStationEnum());	
			}		
		}

		private HorizontalLayout createButtonsLayout() {
		    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		    close.addClickShortcut(Key.ESCAPE);
		    return new HorizontalLayout(save, close); 
		}
		
		private void saveClicked() {
			selectedCar.setCarBrand(brand.getValue());	
			selectedCar.setName(name.getValue());
			selectedCar.setCarSeats(seats.getValue());
			selectedCar.setMileage(mileage.getValue());
			selectedCar.setCarStationEnum(carStation.getValue());
			
			carController.addCar(selectedCar);
		}
		
		@Override
		public void afterNavigation(AfterNavigationEvent event) {	
			carStation.setItems(CarStationEnum.values());
			setValues(selectedCar);
			save.addClickListener(e -> saveClicked());
			close.addClickListener(e -> this.setVisible(false));
			
		}
		
}
