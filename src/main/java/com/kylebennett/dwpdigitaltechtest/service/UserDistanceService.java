package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.SearchResult;

public interface UserDistanceService {

    SearchResult getUsersWithinDistanceOfLocation(String locationName,
                                                  Double distance,
                                                  Double locationLatitude,
                                                  Double locationLongitude);
}
