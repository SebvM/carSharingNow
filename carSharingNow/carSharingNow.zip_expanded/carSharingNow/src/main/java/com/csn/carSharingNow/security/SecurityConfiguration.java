package com.csn.carSharingNow.security;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;

import com.csn.carSharingNow.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;

/**
 * Security Configuration wird überschrieben damit 
 * wir bestimmte einstellungen vornehmen können
 * 
 * @author Sebastian von Minden
 *
 */

@EnableWebSecurity 
@Configuration
public class SecurityConfiguration
                extends VaadinWebSecurityConfigurerAdapter { 


    public static final String LOGOUT_URL = "login";

    @Value("${jwt.auth.secret}")
    private String authSecret;
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }		
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        super.configure(http); 

        setLoginView(http, LoginView.class); 
        
        setStatelessAuthentication(http, new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256), "com.csn.carSharingNow");
        
        http.csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
       
        web.ignoring().antMatchers(
                "/images/**"
        );

        super.configure(web); 
    }
   
    
    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}