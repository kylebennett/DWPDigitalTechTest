
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserClient userClient;

    @GetMapping(value = "/")
    String mainPage(final Model model) {

        Collection<User> allUsers = userClient.getAllUsers();
        model.addAttribute("allUsers", allUsers);

        for (User u : allUsers) {
            log.debug(u.toString());
        }

        return "main";
    }
}