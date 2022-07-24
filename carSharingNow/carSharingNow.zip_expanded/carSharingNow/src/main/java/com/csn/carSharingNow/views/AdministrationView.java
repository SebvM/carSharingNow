package com.csn.carSharingNow.views;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.csn.carSharingNow.controller.CarController;
import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.views.listen.AdministrationCarListView;
import com.csn.carSharingNow.views.listen.AdministrationUserListView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
/**
 * Adminboard führt Fahrzeugübersicht (AdministrationCarListView) und Nutzerübersicht (AdministrationUserListView) zusammen
 * 
 * @author Sebastian von Minden
 *
 */

@SuppressWarnings("serial")
@Route(value = "administration", layout=MainLayout.class)
@PageTitle("Administration | car Sharing Now")
@RolesAllowed("admin")
public class AdministrationView extends VerticalLayout implements AfterNavigationObserver{
	
	
	AdministrationUserListView adminUserView;
	AdministrationCarListView adminCarView;
	
	AdministrationView(@Autowired UserController userController,@Autowired CarController carController){
		adminUserView = new AdministrationUserListView(userController);
		adminCarView = new AdministrationCarListView(carController);
		add(adminUserView,adminCarView);
	}


	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
	}
}
