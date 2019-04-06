/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.services.ListSightingResponse;
import com.sg.superheroSightings.services.SuperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author afahrenkamp
 */
@Controller
public class MainController {

    @Autowired
    SuperService service;

    @GetMapping("/home")
    public String homePage(Model model) {

        String toReturn = "";

        ListSightingResponse response = service.getLastTenSightings();
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {

            model.addAttribute("allSightings", response.getAllSightings());
            toReturn = "/home";

        }
        return toReturn;
    }
}
