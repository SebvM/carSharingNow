package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.models.Role;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    // UT-1 Tests if the user is added like expected
    @Test
    public void returnContainsAddedUser() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Date date = new GregorianCalendar(1995,11,01).getTime();
        User testUserUT1 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",date,userRoles );
        userController.addUser(testUserUT1);
        assertEquals(testUserUT1.getUsername(), userRepository.findByUsername(testUserUT1.getUsername()).getUsername());
        assertEquals(testUserUT1.getEmail(), userRepository.findByUsername(testUserUT1.getUsername()).getEmail());
        assertEquals(testUserUT1.getPassword(), userRepository.findByUsername(testUserUT1.getUsername()).getPassword());
        assertEquals(testUserUT1.getFirstname(), userRepository.findByUsername(testUserUT1.getUsername()).getFirstname());
        assertEquals(testUserUT1.getLastname(), userRepository.findByUsername(testUserUT1.getUsername()).getLastname());
        assertEquals(testUserUT1.getDateOfBirth(), userRepository.findByUsername(testUserUT1.getUsername()).getDateOfBirth());
    }

    // UT-2 Tests if list returns added user
    @Test
    public void returnListContainsAddedUser() throws Exception {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.USER);
        Date date = new GregorianCalendar(1995,11,01).getTime();
        User testUserUT2 = new User("Herb", "herb@gmx.de", "1234", "Herbert",
                "Hansen",date,userRoles );
        userController.addUser(testUserUT2);
        List idList = new ArrayList<>();
        for(User user : userController.getAllUser()){
            if(user.getId()==testUserUT2.getId()){
                idList.add(user.getId());
            }
        }
        assertTrue(idList.contains(testUserUT2.getId()));
    }
}
