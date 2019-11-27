package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;

public interface DistanceCalculatorService {

    //London coordinates from https://latitudelongitude.org/gb/london/
    Coordinates LONDON = new Coordinates(51.50853, -0.12574);

    double calculateDistanceBetween2Points(Coordinates far, Coordinates wide);

}
