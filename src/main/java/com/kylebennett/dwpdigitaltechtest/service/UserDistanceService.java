package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;

import java.util.Set;

public interface UserDistanceService {

    Set<User> getUsersWithinDistanceOfLocation(String locationName,
                                               Double distance,
                                               Double locationLatitude,
                                               Double locationLongitude);
}
