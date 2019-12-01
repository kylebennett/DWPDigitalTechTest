
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.Coordinates;
import com.kylebennett.dwpdigitaltechtest.model.FormSubmission;
import com.kylebennett.dwpdigitaltechtest.model.Locations;
import com.kylebennett.dwpdigitaltechtest.model.SearchResult;
import com.kylebennett.dwpdigitaltechtest.service.UserDistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private UserDistanceService userDistanceService;

    @GetMapping(value = "/")
    String mainPage(final Model model) {

        // Set the default form values to London or 50 miles from London's coordinates
        FormSubmission defaultFormValues = new FormSubmission();
        defaultFormValues.setLocationName(Locations.LONDON.getName());
        defaultFormValues.setDistance(50);
        Coordinates london = Locations.LONDON.getCoordinates();
        defaultFormValues.setLocationLat(london.getLatitude());
        defaultFormValues.setLocationLong(london.getLongitude());

        model.addAttribute("submission", defaultFormValues);
        return "main";
    }

    @PostMapping("/")
    String handleFormSubmission(@ModelAttribute(name = "submission") @Valid FormSubmission submission, BindingResult bindingResult, Model model) {

        log.debug(submission.toString());

        if (bindingResult.hasErrors()) {
            return "main";
        }

        final SearchResult usersWithinDistanceOfLocation = userDistanceService.getUsersWithinDistanceOfLocation(submission.getLocationName(),
                submission.getDistance(),
                submission.getLocationLat(),
                submission.getLocationLong());

        model.addAttribute("errors", usersWithinDistanceOfLocation.getErrorMessages());
        model.addAttribute("users", usersWithinDistanceOfLocation.getUsers());

        return "results";
    }
}
