package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserClientImplTest {

    @Value("${user.api.baseurl}")
    String baseUrl;

    @Mock
    RestTemplate mockTemplate;

    @InjectMocks
    UserClientImpl client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_getAllUsers_200Response_usersReturned() {

        User user1 = new User();
        User user2 = new User();
        User[] mockUsers = {user1, user2};

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/users", User[].class)).thenReturn(new ResponseEntity<>(mockUsers, HttpStatus.OK));
        Collection<User> users = client.getAllUsers();

        assertThat(users).contains(user1, user2);
    }

    @Test
    void test_getAllUsers_200Response_noUsersReturned() {

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/users", User[].class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Collection<User> users = client.getAllUsers();

        assertThat(users).isEmpty();
    }

    @Test
    void test_getAllUsersFromCity_200Response_usersReturned() {

        String city = "London";
        User user1 = new User();
        User user2 = new User();
        User[] mockUsers = {user1, user2};

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/city/London/users", User[].class)).thenReturn(new ResponseEntity<>(mockUsers, HttpStatus.OK));
        Collection<User> users = client.getAllUsersFromCity(city);

        assertThat(users).contains(user1, user2);
    }

    @Test
    void test_getAllUsersFromCity_200Response_noUsersReturned() {

        String city = "London";

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/city/London/users", User[].class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Collection<User> users = client.getAllUsersFromCity(city);

        assertThat(users).isEmpty();
    }


    @Test
    void test_getUserById_200Response_UserReturned() {

        int id = 1;
        User user = new User();

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/user/" + id, User.class)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        Optional<User> returnedUser = client.getUserById(id);
        assertThat(returnedUser).isNotEmpty();
        assertThat(returnedUser.get()).isEqualTo(user);
    }

    @Test
    void test_getUserById_200Response_noUserReturned() {

        int id = 1;

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/user/" + id, User.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Optional<User> returnedUser = client.getUserById(id);
        assertThat(returnedUser).isEmpty();
    }
}