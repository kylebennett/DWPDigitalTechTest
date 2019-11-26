package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

@Service
public class UserClient {

    @Value("${user.api.baseurl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Collection<User> getAllUsers() {

        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/users", User[].class);
        return Arrays.asList(response.getBody());
    }
}
