package com.kylebennett.dwpdigitaltechtest.model;

public enum Locations {

    //Location coordinates from https://latitudelongitude.org/

    LONDON("London", new Coordinates(51.50853, -0.12574)),
    NEWCASTLE("Newcastle", new Coordinates(54.97328, -1.61396)),
    CARDIFF("Cardiff", new Coordinates(51.48, -3.18)),
    EDINBURGH("Edinburgh", new Coordinates(55.95206, -3.19648));

    private final String name;
    private final Coordinates coordinates;

    Locations(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
