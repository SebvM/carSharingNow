package com.csn.carSharingNow.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

@SuppressWarnings("serial")
public class UserDetailsImpl extends User implements UserDetails {

	private User user;
    public UserDetailsImpl(User user) {
        this.user = user;
    }
	


	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
         
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
