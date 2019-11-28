package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;

public interface DistanceCalculatorService {


    double calculateDistanceBetween2Points(Coordinates far, Coordinates wide);

}
