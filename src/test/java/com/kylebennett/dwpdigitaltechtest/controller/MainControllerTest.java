
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MainControllerTest {

    @Mock
    UserClient mockUserClient;

    Model model = new ConcurrentModel();

    @InjectMocks
    MainController controller;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {

    }

    @Test
    public void testMainPage() throws Exception {

        Collection<User> users = new ArrayList<>();
        User user1 = new User();
        users.add(user1);

        Mockito.when(mockUserClient.getAllUsers()).thenReturn(users);

        String retVal = controller.mainPage(model);
        assertThat(retVal).isEqualTo("main");
        assertThat(model.containsAttribute("allUsers"));

        final Collection<User> allUsers = (Collection<User>) model.getAttribute("allUsers");
        assertThat(allUsers).contains(user1);
    }
}
