package com.csn.carSharingNow.security;

import java.util.List;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;

/**
 * UserDetailsService wird implementiert um die 
 * LoadUserByUsername funktion zu ersetzen
 *  * 
 * @author Sebastian von Minden
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityService securityService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<User> users = userRepository.findAll();
        if (user == null && users.size() != 0) {         	
           throw new UsernameNotFoundException("No user present with username: " + username);
        }else if (user == null && users.size() == 0) {
        	securityService.getFirstTimeUser();
        	throw new UsernameNotFoundException("No users exist default admin account has been created.");
        } else {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    getAuthorities(user));
        }
    }

    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());

    }

}
