
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.model.User;
import com.kylebennett.dwpdigitaltechtest.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class MainController {

    @Autowired
    UserClient userClient;

    @GetMapping(value = "/")
    public String mainPage(final Model model) {

        Collection<User> allUsers = userClient.getAllUsers();
        model.addAttribute("allUsers", allUsers);

        return "main";
    }
}
