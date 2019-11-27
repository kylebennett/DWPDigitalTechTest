package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorServiceImplTest {

    static final Coordinates Newcastle = new Coordinates(54.97328, -1.61396);
    static final Coordinates Cardiff = new Coordinates(51.48, -3.18);
    static final Coordinates Edinburgh = new Coordinates(55.95206, -3.19648);

    DistanceCalculatorServiceImpl service = new DistanceCalculatorServiceImpl();

    @Test
    void test_calculateDistanceBetween2Points_SameCoordinates() {

        double distance = service.calculateDistanceBetween2Points(Newcastle, Newcastle);
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
                Arguments.of(Newcastle, Cardiff, 250),
                Arguments.of(Newcastle, Edinburgh, 91),
                Arguments.of(Newcastle, DistanceCalculatorService.LONDON, 247)
        );
    }
}