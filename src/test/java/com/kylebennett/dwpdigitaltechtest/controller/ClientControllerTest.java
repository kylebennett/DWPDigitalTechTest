
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.*;
import com.kylebennett.dwpdigitaltechtest.service.UserDistanceService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

import java.util.Arrays;
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
        String validFormContent = EntityUtils.toString(
                new UrlEncodedFormEntity(
                        Arrays.asList(new BasicNameValuePair("locationName", Locations.LONDON.getName()),
                                new BasicNameValuePair("distance", "50.0"),
                                new BasicNameValuePair("locationLat", london.getLatitude() + ""),
                                new BasicNameValuePair("locationLong", london.getLongitude() + ""))));

        Set<User> users = new HashSet<>();
        users.add(new User());

        SearchResult searchResult = new SearchResult();
        searchResult.setUsers(users);

        when(userDistanceService.getUsersWithinDistanceOfLocation(Locations.LONDON.getName(), 50.0,
                london.getLatitude(), london.getLongitude()))
                .thenReturn(searchResult);

        MvcResult result = mvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(validFormContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("submission"))
                .andExpect(view().name("results")).andReturn();

        final Map<String, Object> model = result.getModelAndView().getModel();

        Set<User> returnedUsers = (Set<User>) model.get("users");
        assertThat(returnedUsers).hasSize(1);
    }

    @Test
    void test_handleFormSubmission_formValidationErrors() throws Exception {

        Coordinates london = Locations.LONDON.getCoordinates();

        String invalidFormContent = EntityUtils.toString(
                new UrlEncodedFormEntity(
                        Arrays.asList(new BasicNameValuePair("locationName", null),
                                new BasicNameValuePair("distance", "-50.0"),
                                new BasicNameValuePair("locationLat", london.getLatitude() + ""),
                                new BasicNameValuePair("locationLong", london.getLongitude() + ""))));

        Set<User> users = new HashSet<>();
        users.add(new User());

        SearchResult searchResult = new SearchResult();
        searchResult.setUsers(users);

        when(userDistanceService.getUsersWithinDistanceOfLocation(null, 0.0,
                0.0, 0.0))
                .thenReturn(searchResult);

        MvcResult result = mvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(invalidFormContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("submission"))
                .andExpect(view().name("main")).andReturn();
    }
}
