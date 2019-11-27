package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);

    @Value("${user.api.baseurl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Collection<User> getAllUsers() {

        log.debug("Getting Users");
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/users", User[].class);

        Collection<User> users = new ArrayList<>();

        if (response.getBody() != null) {
            users = Arrays.asList(response.getBody());
        }

        log.debug("[" + users.size() + "] Users returned");
        return users;
    }
}
