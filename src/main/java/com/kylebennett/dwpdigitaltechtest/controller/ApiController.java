package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.UserDistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ApiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDistanceService userDistanceService;

    @GetMapping(value = "/api/users-within-fifty-miles-of-london")
    public Set<User> getUsersWithin50MilesOfLondon() {

        Coordinates london = Locations.LONDON.getCoordinates();
        return userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), 50.0, london.getLatitude(), london.getLongitude());
    }

    @GetMapping("/api/users-within-distance-of-location")
    public Set<User> getUsersWithinDistanceOfLocation(@RequestParam(name = "locationName") String locationName,
                                                      @RequestParam(name = "distance") Double distance,
                                                      @RequestParam(name = "locationLat") Double locationLatitude,
                                                      @RequestParam(name = "locationLong") Double locationLongitude) {

        return userDistanceService.getUsersWithinDistanceOfLocation(locationName,
                distance,
                locationLatitude,
                locationLongitude);
    }
}
