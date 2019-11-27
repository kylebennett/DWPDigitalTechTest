package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.DistanceCalculatorService;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class ApiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserClient userClient;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @GetMapping(value = "/api/users-within-fifty-miles-of-london")
    public List<User> getUsersWithin50MilesOfLondon() {

        log.debug("Searching for users within 50 miles of London");

        //get users in London
        ArrayList<User> usersNearLondon = new ArrayList<>(userClient.getAllUsersFromCity(UserClient.LONDON));

        //get users within 50 miles of London
        Collection<User> allUsers = userClient.getAllUsers();

        for (User user : allUsers) {
            if (!usersNearLondon.contains(user)) {

                Coordinates userCoords = new Coordinates(user.getLatitude(), user.getLongitude());
                double distanceFromLondon = distanceCalculatorService.calculateDistanceBetween2Points(userCoords, DistanceCalculatorService.LONDON);

                if (distanceFromLondon <= 50) {
                    usersNearLondon.add(user);
                }
            }
        }

        log.debug("[" + usersNearLondon.size() + "] Users found within 50 miles of London");

        return usersNearLondon;
    }
}
