package com.csn.carSharingNow;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("deprecation")
@RestController
public class UiApplication {

  @RequestMapping("/")
  public Map<String,Object> home() {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World");
    return model;
  }

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }
  
  @Configuration
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .httpBasic()
      .and()
        .authorizeRequests()
          .antMatchers("/index.html", "/", "/home", "/login").permitAll()
          .anyRequest().authenticated();
    }
  }
}
