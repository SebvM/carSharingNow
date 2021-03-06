package com.csn.carSharingNow.security;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;


/**
 *  Componente die uns die authentication und den
 *   angemeldeten nutzer wieder gibt sowie den logout verwaltet 
 * 
 * @author Sebastian von Minden
 *
 */

@Component
public class SecurityService {
    
    @Autowired
    private UserRepository userRepository;    
  
    
    public static Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<User> get() {
        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()));
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
    
    public void getFirstTimeUser() {
    	List<Role> adminroles = new ArrayList<Role>();
        adminroles.add(Role.ADMIN);
        Date dateofDeath = new Date(System.currentTimeMillis());
    	User defaultUser = new User("admin", "admin@cSn.de", "rotierendeente", "Admin", "Adminson", dateofDeath, adminroles);
    	userRepository.save(defaultUser);
        System.out.println(defaultUser);
		
    }
}
