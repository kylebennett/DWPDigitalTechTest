package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import com.kylebennett.dwpdigitaltechtest.model.SearchResult;
import com.kylebennett.dwpdigitaltechtest.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserDistanceServiceImplTest {

    @Mock
    UserClient userClient;

    @Mock
    DistanceCalculatorService distanceCalculatorService;

    @InjectMocks
    UserDistanceServiceImpl userDistanceService;

    private Coordinates close, far, london;
    private User londonUser, closeUser, farUser;
    private Collection<User> londonUsers;
    private Collection<User> allUsers;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        close = new Coordinates(5.0, 5.0);
        far = new Coordinates(20.0, 20.0);

        london = Locations.LONDON.getCoordinates();

        londonUser = new User();
        londonUser.setId(1);
        londonUser.setLatitude(far.getLatitude());
        londonUser.setLongitude(far.getLongitude());

        closeUser = new User();
        closeUser.setId(2);
        closeUser.setLatitude(close.getLatitude());
        closeUser.setLongitude(close.getLongitude());

        farUser = new User();
        farUser.setId(3);
        farUser.setLatitude(far.getLatitude());
        farUser.setLongitude(far.getLongitude());

        londonUsers = new ArrayList<>();
        londonUsers.add(londonUser);

        allUsers = new ArrayList<>();
        allUsers.add(londonUser);
        allUsers.add(closeUser);
        allUsers.add(farUser);

        Mockito.when(userClient.getAllUsersFromCity(Locations.LONDON.getName())).thenReturn(londonUsers);
        Mockito.when(userClient.getAllUsers()).thenReturn(allUsers);
        Mockito.when(distanceCalculatorService.calculateDistanceBetween2Points(close, london)).thenReturn(25.0);
        Mockito.when(distanceCalculatorService.calculateDistanceBetween2Points(far, london)).thenReturn(100.0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_allParameters() {

        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), 50.0, london.getLatitude(), london.getLongitude());
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(2);
        assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser, closeUser);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_noParameters() {

        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, null, null, null);
        assertThat(withinDistanceOfLocation.getUsers()).isEmpty();
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_nameOnly() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), null, null, null);
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
        assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_coordsOnly() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), london.getLongitude());
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
        assertThat(withinDistanceOfLocation.getUsers()).contains(closeUser);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_distanceLessThan0() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, -50.0, london.getLatitude(), london.getLongitude());
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(0);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(1);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_latitudeLessThanMinus90() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, 50.0, -91.0, london.getLongitude());
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(0);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(1);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_latitudeGreaterThan90() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, 50.0, 91.0, london.getLongitude());
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(0);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(1);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_longitudeGreaterThan180() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), 181.0);
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(0);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(1);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_longitudeLessThanMinus180() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), -181.0);
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(0);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(1);
    }

    @Test
    void test_getUsersWithinDistanceOfLocation_nameSearch_InvalidCoords() {
        SearchResult withinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), -50.0, -91.0, -181.0);
        assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
        assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser);
        assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(3);
    }
}