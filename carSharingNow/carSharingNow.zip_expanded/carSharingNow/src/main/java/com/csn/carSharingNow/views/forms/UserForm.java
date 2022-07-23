package com.csn.carSharingNow.views.forms;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.models.User;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
public class UserForm extends FormLayout implements AfterNavigationObserver {	

	@Autowired
	UserController userController;
	
	private User selectedUser;
	
	
	private H4 carFormHeader = new H4("Benutzerrolle anpassen");
	private TextField username = new TextField("Benutzername");
	private TextField firstname= new TextField("Vorname");
	private TextField lastname= new TextField("Nachname");
	private DatePicker dateOfBirth= new DatePicker("Geburtstag");
	private TextField email= new TextField("Email");
	private ComboBox<Role> role = new ComboBox<Role>("Rolle");
	
	private Button save = new Button("Speichern");
	private Button close = new Button("Zurück"); 
	  
	public UserForm(User selectedUser) {
		this.selectedUser = selectedUser;  
		addClassName("user-form"); 
		username.setReadOnly(true);
		
		add(carFormHeader,
			username,
			firstname,
			lastname,
			dateOfBirth,
			email,
			role,
			createButtonsLayout());    
	}
	
	private HorizontalLayout createButtonsLayout() {
	    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

	    close.addClickShortcut(Key.ESCAPE);
	    return new HorizontalLayout(save, close); 
	}
	
	private void saveClicked() {
		if(!role.getValue().equals(selectedUser.roles.get(0))) {
			selectedUser.roles.clear();
			selectedUser.roles.add(role.getValue());			
			userController.addUser(selectedUser);

			this.setVisible(false);
		}
		
	}
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		role.setItems(Role.values());
		if(selectedUser != null)
			role.setValue(selectedUser.roles.get(0));

		save.addClickListener(e -> saveClicked());
		close.addClickListener(e -> this.setVisible(false));
		
	}
	
}
