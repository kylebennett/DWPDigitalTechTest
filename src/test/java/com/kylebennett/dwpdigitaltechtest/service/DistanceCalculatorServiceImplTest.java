package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
class DistanceCalculatorServiceImplTest {

    private DistanceCalculatorServiceImpl service = new DistanceCalculatorServiceImpl();

    @Test
    void test_calculateDistanceBetween2Points_SameCoordinates() {

        double distance = service.calculateDistanceBetween2Points(Locations.NEWCASTLE.getCoordinates(), Locations.NEWCASTLE.getCoordinates());
        assertThat(distance).isEqualTo(0);

    }

    @ParameterizedTest(name = "Run: {index} - Point1: {0}, Point2: {1}, Expected Distance= {2}")
    @MethodSource("test_Coordinates")
    void test_calculateDistanceBetween2Points(Coordinates point1, Coordinates point2, double expectedDistance) {

        double actualDistance = service.calculateDistanceBetween2Points(point1, point2);
        assertThat(actualDistance).isCloseTo(expectedDistance, Percentage.withPercentage(2));
    }

    static Stream<Arguments> test_Coordinates() {
        return Stream.of(
                Arguments.of(Locations.NEWCASTLE.getCoordinates(), Locations.CARDIFF.getCoordinates(), 250),
                Arguments.of(Locations.NEWCASTLE.getCoordinates(), Locations.EDINBURGH.getCoordinates(), 91),
                Arguments.of(Locations.NEWCASTLE.getCoordinates(), Locations.LONDON.getCoordinates(), 247)
        );
    }
}