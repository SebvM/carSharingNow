package com.csn.carSharingNow.views.listen;


import javax.annotation.security.RolesAllowed;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.models.Car;
import com.csn.carSharingNow.views.forms.CarForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
/**
 * ListView für die Fahrzeuganzeige im Adminboard
 * 
 * @author Sebastian von Minden
 *
 */
@SuppressWarnings("serial")
@RolesAllowed("admin")
public class AdministrationCarListView extends VerticalLayout implements AfterNavigationObserver{
	
	CarController carController;
	
	Car selectedCar;
	H3 carlistheader;
	CarForm carForm;
	Grid<Car> grid = new Grid<>(Car.class); 	
	
	Button addCarButton = new Button("Fahrzeug hinzufügen");
	
	public AdministrationCarListView(CarController carController) {
		this.carController = carController;
		carlistheader = new H3("Fahrzeuge: "+ carController.getAllCars().size());
        addClassName("reservation-list-view");
        setSizeFull();
        configureGrid(); 
        configureForm();        
        add(carlistheader, getToolbar(), getContent()); 
    }

	
	private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setColumns("name","carBrand", "mileage", "carSeats");  
        
        grid.addColumn("carStationEnum").setHeader("Station");
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("60%");
        grid.setSizeUndefined();
        grid.addSelectionListener(e-> { 
        	if(e.getFirstSelectedItem().isPresent() ) {
        		selectedItemEvent(e.getFirstSelectedItem().get());
        	}
        } );
    }
    
    private void configureForm() {
    	carForm = new CarForm(null); 
    	carForm.setWidth("25em");
    	carForm.addDetachListener(e-> gridUpdate());
    }
    
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, carForm);
        content.setFlexGrow(2, grid); 
        content.setFlexGrow(1, carForm);
        carForm.setVisible(false);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    
    private HorizontalLayout getToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout(); 
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.add(addCarButton);
        return toolbar;
    }
    
    private void gridUpdate() {
    	grid.setItems(carController.getAllCars() );
	}
    
    public void setCarFormData(Car selectedCar) {
    	carForm.setSelectedCar(selectedCar);
    	carForm.setVisible(true);    	
	}
   
    private void selectedItemEvent(Car selectedCar) {
    	if(selectedCar != null) {
        	carForm.setValues(selectedCar);
    	}
    		
    }
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		gridUpdate();		

    	addCarButton.addClickListener(e-> addCarButtonEvent());
		grid.addSelectionListener(e -> e.getFirstSelectedItem().ifPresent(car -> setCarFormData(selectedCar = e.getFirstSelectedItem().get())) );
		carForm.setCarController(carController);
		carForm.addClickListener(e-> gridUpdate());
	}
	
	public void addCarButtonEvent() {
		Car newCar = new Car();
		carForm.setSelectedCar(newCar);
		carForm.clearForm();
		carForm.setVisible(true);
	}
	
}
