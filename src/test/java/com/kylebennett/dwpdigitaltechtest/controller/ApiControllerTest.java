package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.UserDistanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserDistanceService userDistanceService;

    @MockBean
    RestTemplate template;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_getUsersWithin50MilesOfLondon() throws Exception {

        Coordinates london = Locations.LONDON.getCoordinates();
        Set<User> users = new HashSet<>();
        users.add(new User());

        when(userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), 50.0,
                london.getLatitude(), london.getLongitude()))
                .thenReturn(users);

        MvcResult result = mvc.perform(get("/api/users-within-fifty-miles-of-london")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andReturn();


    }

    @Test
    void test_getUsersWithinDistanceOfLocation() throws Exception {

        Coordinates london = Locations.LONDON.getCoordinates();
        Set<User> users = new HashSet<>();
        users.add(new User());

        when(userDistanceService.getUsersWithinDistanceOfLocation("locationName", 50.0,
                1.0, 1.0))
                .thenReturn(users);

        MvcResult result = mvc.perform(get("/api/users-within-distance-of-location")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("locationName", "locationName")
                .param("distance", "50.0")
                .param("locationLat", "1.0")
                .param("locationLong", "1.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();
    }
}