package com.kylebennett.dwpdigitaltechtest.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

public class FormSubmission {

    private String locationName;

    @Min(0)
    private double distance;

    @Min(-90)
    @Max(90)
    private double locationLat;

    @Min(-180)
    @Max(180)
    private double locationLong;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormSubmission)) return false;
        FormSubmission that = (FormSubmission) o;
        return Double.compare(that.distance, distance) == 0 &&
                Double.compare(that.locationLat, locationLat) == 0 &&
                Double.compare(that.locationLong, locationLong) == 0 &&
                Objects.equals(locationName, that.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, distance, locationLat, locationLong);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("locationName", locationName)
                .append("distance", distance)
                .append("locationLat", locationLat)
                .append("locationLong", locationLong)
                .toString();
    }
}
