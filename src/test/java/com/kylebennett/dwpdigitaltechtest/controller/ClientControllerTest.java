
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.FormSubmission;
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
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserDistanceService userDistanceService;

    @MockBean
    RestTemplate template;

    private FormSubmission defaultFormValues;

    @BeforeEach
    void setUp() throws Exception {

        defaultFormValues = new FormSubmission();
        defaultFormValues.setLocationName(Locations.LONDON.getName());
        defaultFormValues.setDistance(50);
        Coordinates london = Locations.LONDON.getCoordinates();
        defaultFormValues.setLocationLat(london.getLatitude());
        defaultFormValues.setLocationLong(london.getLongitude());
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void test_get_MainPage() throws Exception {

        MvcResult result = mvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("submission"))
                .andExpect(view().name("main")).andReturn();

        final Map<String, Object> model = result.getModelAndView().getModel();

        assertThat(model.get("submission")).isEqualTo(defaultFormValues);
    }

    @Test
    void test_handleFormSubmission() throws Exception {

        Coordinates london = Locations.LONDON.getCoordinates();
        Set<User> users = new HashSet<>();
        users.add(new User());

        when(userDistanceService.getUsersWithinDistanceOfLocation(null, 0.0,
                0.0, 0.0))
                .thenReturn(users);

        MvcResult result = mvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED).sessionAttr("submission", defaultFormValues))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("submission"))
                .andExpect(view().name("results")).andReturn();

        final Map<String, Object> model = result.getModelAndView().getModel();

        Set<User> returnedUsers = (Set<User>) model.get("users");
        assertThat(returnedUsers).hasSize(1);
    }
}
