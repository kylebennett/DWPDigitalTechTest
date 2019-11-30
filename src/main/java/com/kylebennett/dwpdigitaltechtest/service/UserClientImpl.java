package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserClientImpl implements UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClientImpl.class);

    @Value("${user.api.baseurl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Collection<User> getAllUsers() {

        log.debug("Getting Users");
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/users", User[].class);

        Collection<User> users = parseResponse(response);

        log.debug("[" + users.size() + "] Users returned");
        return users;
    }

    @Override
    public Collection<User> getAllUsersFromCity(String city) {

        log.debug("Getting Users from City [" + city + "]");
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/city/" + city + "/users", User[].class);

        Collection<User> users = parseResponse(response);

        for (User u : users) {
            u.setCity(city);
        }

        log.debug("[" + users.size() + "] Users returned from City [" + city + "]");
        return users;
    }

    @Override
    public Optional<User> getUserById(int id) {

        log.debug("Getting User with ID [" + id + "]");
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/user/" + id, User.class);

        return Optional.ofNullable(response.getBody());
    }

    private Collection<User> parseResponse(ResponseEntity<User[]> response) {
        Collection<User> users = new ArrayList<>();

        if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
            users = Arrays.asList(response.getBody());
        }
        return users;
    }
}
