package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTesting;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTesting.class,
        properties = {
                "command.line.runner.enabled=false"
        })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
        for (User u : myList){
            System.out.println(u.getUserid()+ " "+ u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById() {
        assertEquals("puttat", userService.findUserById(13).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void aa_findUserByIdNotFound() {
        assertEquals("", userService.findUserById(9999999).getUsername());
    }

    @Test
    public void b_findByNameContaining() {
        assertEquals(1, userService.findByNameContaining("barn").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_findByName() {
        assertEquals("barnbarn", userService.findByName("barnbarn").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dd_findByNameNotFound() {
        assertEquals("", userService.findByName("barn").getUsername());
    }

    @Test
    public void e_save() {
        String name = "chocotest";
        User u = new User ("chocoTest","password", "chocoTest@chookie.ch");
        Role r = new Role("food");
        r.setRoleid(3);
        u.getRoles()
                .add(new UserRoles(u, r));
        User addU = userService.save(u);
        assertNotNull(addU);
        assertEquals(name, addU.getUsername());
    }

    @Test
    public void ee_savePut() {
        String name = "chocolatetest";
        User u = new User ("chocolateTest","password", "chookieTest@chookie.ch");
        u.setUserid(14);
        Role r = new Role("food");
        r.setRoleid(3);
        u.getRoles()
                .add(new UserRoles(u, r));
        User addU = userService.save(u);
        assertNotNull(addU);
        assertEquals(name, addU.getUsername());
    }


    @Test
    public void g_delete() {
        userService.delete(11);
        assertEquals(5, userService.findAll().size());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void gg_deleteUserNotFound() {
        userService.delete(1500);
        assertEquals(5, userService.findAll().size());
    }
    @Test
    public void ggg_deleteAll() {
    }
}