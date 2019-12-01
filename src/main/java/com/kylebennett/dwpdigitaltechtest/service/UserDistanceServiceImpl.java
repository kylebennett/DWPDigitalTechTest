package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.SearchResult;
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
    public SearchResult getUsersWithinDistanceOfLocation(String locationName, Double distance, Double locationLatitude, Double locationLongitude) {

        SearchResult results = new SearchResult();
        Set<User> users = new HashSet<>();

        // If a location name has been provided look up users from that city
        if (!(locationName == null) && !locationName.isEmpty()) {
            log.debug("Getting users from {}", locationName);
            users.addAll(userClient.getAllUsersFromCity(locationName));
        } else {
            log.debug("No location name provided");
        }

        // If coordinates and distance are provided find all users with in range
        if (distance != null && locationLatitude != null && locationLongitude != null) {
            log.debug("Getting users within [{}] miles of [{} , {}]", distance, locationLatitude, locationLongitude);

            boolean validParams = true;

            if (distance < 0) {
                //invalid distance
                results.getErrorMessages().add("Distance must be greater than 0");
                validParams = false;
            }


            if (locationLatitude > 90 || locationLatitude < -90) {
                //invalid latitude
                results.getErrorMessages().add("Latitude must be must be between 90 and -90");
                validParams = false;
            }

            if (locationLongitude > 180 || locationLongitude < -180) {
                //invalid longitude
                results.getErrorMessages().add("Longitude must be between 180 and -180");
                validParams = false;
            }

            if (validParams) {
                Coordinates coords = new Coordinates(locationLatitude, locationLongitude);
                users.addAll(getUsersWithinRangeOfCoords(distance, coords));
            }

        } else {
            log.debug("No coordinates provided");
        }

        log.debug("[{}] users found", users.size());
        results.setUsers(users);

        return results;
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
