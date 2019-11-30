package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDistanceServiceImpl implements UserDistanceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserClient userClient;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @Override
    public Set<User> getUsersWithinDistanceOfLocation(String locationName, Double distance, Double locationLatitude, Double locationLongitude) {

        Set<User> users = new HashSet<>();

        // If a location name has been provided look up users from that city
        if (!(locationName == null) && !locationName.isEmpty()) {
            log.debug("Getting users from {}", locationName);
            users.addAll(userClient.getAllUsersFromCity(locationName));
        } else {
            log.debug("No location name provided");
        }

        // If coordinates and distance are provided find all users with in range
        if (distance != null && distance > 0 && locationLatitude != null && locationLongitude != null) {
            log.debug("Getting users within [{}] miles of [{} , {}]", distance, locationLatitude, locationLongitude);
            Coordinates coords = new Coordinates(locationLatitude, locationLongitude);
            users.addAll(getUsersWithinRangeOfCoords(distance, coords));
        } else {
            log.debug("No coordinates provided");
        }

        log.debug("[{}] users found", users.size());
        return users;
    }

    private List<User> getUsersWithinRangeOfCoords(double distance, Coordinates location) {

        List<User> usersWithinRange = new ArrayList<>();

        // Get all users
        Collection<User> allUsers = userClient.getAllUsers();

        for (User user : allUsers) {

            // Calculate the distance between the user and the coordinates provided
            Coordinates userCoords = new Coordinates(user.getLatitude(), user.getLongitude());
            double distanceFromCoords = distanceCalculatorService.calculateDistanceBetween2Points(userCoords, location);

            // If the user is within range add it to the list
            if (distanceFromCoords <= distance) {
                user.setDistanceFromCoords(distanceFromCoords);
                usersWithinRange.add(user);
            }
        }

        log.debug("[{}] users found within range", usersWithinRange.size());
        return usersWithinRange;
    }
}
