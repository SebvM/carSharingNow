package com.csn.carSharingNow.ControllerTest;

import com.csn.carSharingNow.controller.UserController;
import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    //
    @Test
    public void returnContainsAddedUser() throws Exception {
        User testUserUT1 = new User("Hille", "hille@gmx.de", "1234", "Hilde",
                "Hansen",new Date (2022,11,01), );
        userController.addUser(testUserUT1);
        assertEquals(testUserUT1.getUsername(), userRepository.findByUsername(testUserUT1.getUsername()));
        assertEquals(testUserUT1.getEmail(), userRepository.findByUsername(testUserUT1.getUsername()).getEmail());
        assertEquals(testUserUT1.getPassword(), userRepository.findByUsername(testUserUT1.getUsername()).getPassword());
        assertEquals(testUserUT1.getFirstname(), userRepository.findByUsername(testUserUT1.getUsername()).getFirstname());
        assertEquals(testUserUT1.getLastname(), userRepository.findByUsername(testUserUT1.getUsername()).getLastname());
        assertEquals(testUserUT1.getRoles(), userRepository.findByUsername(testUserUT1.getUsername()).getRoles());
        assertEquals(testUserUT1.getDateOfBirth(), userRepository.findByUsername(testUserUT1.getUsername()).getDateOfBirth());
    }
    //
    /*@Test
    public void returnContainsAddedUser() throws Exception {
        User testUserUT2 = new User("Hansi", "hansi@gmx.de", "1234", "Hans",
                "Hansen",new Date (2022,11,01), );
        userController.addUser(testUserUT2);
        assertEquals(testUserUT2.getUsername(), userRepository.findByUsername(testUserUT2.getUsername()));
    }*/
    //
    @Test
    public void returnDoesNotContainsAddedUser() throws Exception {
        User testUserUT3 = new User("Holle", "holle@gmx.de", "1234", "Holger",
                "Hansen",new Date (2022,11,01), );
        userController.addUser(testUserUT3);
        User testUserUT = new User("Ralle", "ralle@gmx.de", "1234", "Ralf",
                "Hansen",new Date (2022,11,01), );
        userController.addUser(testUserUT3);
        assertNotEquals(testUserUT.getUsername(), userRepository.findByUsername(testUserUT3.getUsername()));
    }
}
