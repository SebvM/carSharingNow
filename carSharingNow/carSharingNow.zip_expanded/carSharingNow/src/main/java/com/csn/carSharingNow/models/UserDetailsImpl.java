package com.csn.carSharingNow.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User Modell welches die UserDetails der Spring Security 
 * ersetzt damit wir diese in der DB speichern können und uns damit Anmelden.
 * 
 * @author Sebastian von Minden
 *
 */

public class UserDetailsImpl extends User implements UserDetails {

    public UserDetailsImpl(User user) {
        super(user);
    }
	
	public void grantAuthority(Role authority) {
	    if ( roles == null ) roles = new HashSet<>();
	    roles.add(authority);
	}

	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> authorities = new ArrayList<>();
	        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
	        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getFirstname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
