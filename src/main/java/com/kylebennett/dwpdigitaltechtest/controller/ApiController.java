package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.DistanceCalculatorService;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ApiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserClient userClient;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @GetMapping(value = "/api/users-within-fifty-miles-of-london")
    public Set<User> getUsersWithin50MilesOfLondon() {

        Coordinates london = Locations.LONDON.getCoordinates();
        return getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), 50.0, london.getLatitude(), london.getLongitude());
    }

    @GetMapping("/api/users-within-distance-of-location")
    public Set<User> getUsersWithinDistanceOfLocation(@RequestParam(name = "locationName") String locationName,
                                                      @RequestParam(name = "distance") Double distance,
                                                      @RequestParam(name = "locationLat") Double locationLatitude,
                                                      @RequestParam(name = "locationLong") Double locationLongitude) {

        Set<User> users = new HashSet<>();

        // If a location name has been provided look up users from that city
        if (locationName != null) {
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
                usersWithinRange.add(user);
            }
        }

        log.debug("[{}] users found within range", usersWithinRange.size());
        return usersWithinRange;
    }
}
