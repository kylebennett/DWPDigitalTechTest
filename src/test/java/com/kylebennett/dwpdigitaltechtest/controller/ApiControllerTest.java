package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.service.DistanceCalculatorService;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ApiControllerTest {

    @Mock
    UserClient userClient;

    @Mock
    DistanceCalculatorService distanceCalculatorService;

    @InjectMocks
    ApiController controller;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_getUsersWithin50MilesOfLondon() {

    }
}