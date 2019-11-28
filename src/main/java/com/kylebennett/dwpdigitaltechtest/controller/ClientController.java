
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.service.DistanceCalculatorService;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @GetMapping(value = "/")
    String mainPage(final Model model) {

        return "main";
    }
}
