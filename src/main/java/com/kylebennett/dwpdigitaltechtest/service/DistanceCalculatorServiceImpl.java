package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorServiceImpl implements DistanceCalculatorService {

    private static final double EARTH_RADIUS = 3958.8;

    @Override
    public double calculateDistanceBetween2Points(Coordinates far, Coordinates wide) {

        if ((far.getLatitude() == wide.getLatitude()) && (far.getLongitude() == wide.getLongitude())) {
            return 0;
        } else {

            double farLon = Math.toRadians(far.getLongitude());
            double farLat = Math.toRadians(far.getLatitude());

            double wideLon = Math.toRadians(wide.getLongitude());
            double wideLat = Math.toRadians(wide.getLatitude());

            // Haversine formula
            double lonDist = wideLon - farLon;
            double latDist = wideLat - farLat;
            double a = Math.pow(Math.sin(latDist / 2), 2)
                    + Math.cos(farLat) * Math.cos(wideLat)
                    * Math.pow(Math.sin(lonDist / 2), 2);

            double c = 2 * Math.asin(Math.sqrt(a));

            // calculate the result
            return (c * EARTH_RADIUS);
        }
    }
}
