package com.csn.carSharingNow.views.listen;


import java.time.ZoneId;

import javax.annotation.security.RolesAllowed;

import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.views.forms.UserForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
/**
 * ListView für die Nutzeranzeige im Adminboard
 * 
 * @author Sebastian von Minden
 *
 */
@SuppressWarnings("serial")
@RolesAllowed("admin")
public class AdministrationUserListView extends VerticalLayout implements AfterNavigationObserver {
	
	
	UserController userController;
	
	User selectedUser;
	H3 userlistheader;
	UserForm userForm;
	Grid<User> grid = new Grid<>(User.class); 
	
	public AdministrationUserListView(UserController userController) {
		this.userController = userController;
		userlistheader = new H3("Benutzer: "+ userController.getAllUser().size());
        addClassName("reservation-list-view");
        setSizeFull();
        configureGrid(); 
        configureForm();        
        add(userlistheader, getContent()); 
    }

	
	private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setColumns("username","email", "firstname", "lastname");   
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setSizeUndefined();
    }
    
    private void configureForm() {
    	userForm = new UserForm(null); 
    	userForm.setWidth("25em");
    }
    
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, userForm);
        content.setFlexGrow(2, grid); 
        content.setFlexGrow(1, userForm);
        userForm.setVisible(false);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    
    private void gridUpdate() {
    	grid.setItems(userController.getAllUser() );
	}
    
    public void setUserFormData(User selectedUser) {
    	userForm.setSelectedUser(selectedUser);
    	userForm.setVisible(true);
    	userForm.getUsername().setValue(selectedUser.getUsername());
    	userForm.getFirstname().setValue(selectedUser.getFirstname());
    	userForm.getLastname().setValue(selectedUser.getLastname());
    	userForm.getDateOfBirth().setValue(selectedUser.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    	userForm.getEmail().setValue(selectedUser.getEmail());
    	userForm.getRole().setValue(selectedUser.getRoles().get(0));
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		gridUpdate();		
		grid.addSelectionListener(e -> e.getFirstSelectedItem().ifPresent(user -> setUserFormData(selectedUser = e.getFirstSelectedItem().get())) );
		userForm.setUserController(userController);
	}
}
