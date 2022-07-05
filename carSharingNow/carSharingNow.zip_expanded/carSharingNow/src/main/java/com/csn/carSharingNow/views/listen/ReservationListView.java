package com.csn.carSharingNow.views.listen;

import javax.annotation.security.PermitAll;

import com.csn.carSharingNow.models.Reservation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

@Route("")
@PermitAll
@PageTitle("Reservierungen | car Sharing Now")
public class ReservationListView extends VerticalLayout { 
	
    Grid<Reservation> grid = new Grid<>(Reservation.class); 

    public ReservationListView() {
        addClassName("reservation-list-view");
        setSizeFull();
        configureGrid(); 

        add(getToolbar(), grid); 
    }

    private void configureGrid() {
        grid.addClassNames("reservation-grid");
        grid.setSizeFull();
        grid.setColumns("Fahrzeug", "Startdatum", "Enddatum");       
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
    }

    private HorizontalLayout getToolbar() {

        Button addContactButton = new Button("Reservierung hinzufügen");

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton); 
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}