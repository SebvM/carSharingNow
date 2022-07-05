package com.csn.carSharingNow.views;

import java.time.ZoneId;
import java.util.Date;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import com.csn.carSharingNow.security.SecurityService;
import com.csn.carSharingNow.views.forms.UserDataForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "userData", layout=MainLayout.class)
@PermitAll
public class UserDataView extends VerticalLayout {
	
	PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SecurityService securityService;

	private final UserDataForm dataForm = new UserDataForm(); 
	
	public UserDataView() {
		
		H1 header = new H1("Bernutzerdaten");
		addClassName("userData-view");
		setSizeUndefined(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER, dataForm);
		setPadding(true);
		
		if(dataForm.getUsernameField().isInvalid() || dataForm.getFirstnameField().isInvalid() || dataForm.getLastnameField().isInvalid() 
				||dataForm.getDateOfBirthField().isInvalid() || dataForm.getEmailField().isInvalid() || dataForm.getPasswordField().isInvalid() 
				||dataForm.getPasswordConfirmField().isInvalid()) {
			dataForm.getSubmitButton().setEnabled(false);
		}		
		
		setHorizontalComponentAlignment(Alignment.CENTER, header);
		
		dataForm.getSubmitButton().addClickListener(e -> updateUserData());

		add(getContent());	
	}
	
	private Component getContent() {
        VerticalLayout content = new VerticalLayout(dataForm); 
        content.setFlexGrow(1, dataForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }	
		
	private void updateUserData() {
		if(securityService != null) {
			if (securityService.get().isPresent()) {	 
				User user = securityService.get().get();
				user.setDateOfBirth(Date.from(dataForm.getDateOfBirthField().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				user.setFirstname(dataForm.getFirstnameField().getValue());
				user.setLastname(dataForm.getLastnameField().getValue());	
				if(!dataForm.getPasswordField().isEmpty()) {
					if(dataForm.getPasswordConfirmField().getValue().equals(dataForm.getPasswordField().getValue())){
						
						user.setPassword(passwordEncoder.encode(dataForm.getPasswordField().getValue()));	
					
					}
				}			
				
				   
				System.out.println(user.toString());
				userRepository.save(user);
			}
		}	
	}
}
