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

import static org.assertj.core.api.Assertions.assertThat;

class UserClientTest {

    @Value("${user.api.baseurl}")
    private String baseUrl;

    @Mock
    RestTemplate mockTemplate;

    @InjectMocks
    UserClient client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_getAllUsers_200Response() {

        User user1 = new User();
        User user2 = new User();

        User[] mockUsers = {user1, user2};

        Mockito.when(mockTemplate.getForEntity(baseUrl + "/users", User[].class)).thenReturn(new ResponseEntity<>(mockUsers, HttpStatus.OK));

        Collection<User> users = client.getAllUsers();

        assertThat(users.contains(user1));
        assertThat(users.contains(user2));
    }
}