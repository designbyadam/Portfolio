/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.services.AddLocationResponse;
import com.sg.superheroSightings.services.DeleteLocationResponse;
import com.sg.superheroSightings.services.EditLocationResponse;
import com.sg.superheroSightings.services.GetLocationResponse;
import com.sg.superheroSightings.services.GetOrganizationResponse;
import com.sg.superheroSightings.services.ListLocationResponse;
import com.sg.superheroSightings.services.SuperService;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author afahrenkamp
 */
@Controller
public class LocationController {

    @Autowired
    SuperService service;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("/locations")
    public String listLocations(Model model) {
        String toReturn = "";
        ListLocationResponse response = service.getAllLocations();
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            model.addAttribute("allLocations", response.getAllLocations());
            toReturn = "locations";
        }
        return toReturn;

    }

    @GetMapping("/locationDetail")
    public String locationDetail(Integer locationId, Model model) {
        String toReturn = "";

        GetLocationResponse response = service.getLocation(locationId);
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            model.addAttribute("location", response.getToDisplay());

            toReturn = "locationDetail";
        }
        return toReturn;

    }

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute Location toAdd, Model model) {
        String toReturn = "";
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(toAdd);

        if (violations.isEmpty()) {
            AddLocationResponse response = service.addLocation(toAdd);

            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                toReturn = listLocations(model);
            }
        } else {
            model.addAttribute("name", toAdd.getName());
            model.addAttribute("description", toAdd.getDescription());
            model.addAttribute("address", toAdd.getAddress());
            model.addAttribute("latitude", toAdd.getLatitude());
            model.addAttribute("longitude", toAdd.getLongitude());

            toReturn = listLocations(model);
        }

        model.addAttribute("errors", violations);

        return toReturn;
    }

    @GetMapping("/deleteLocation")
    public String deleteLocation(@RequestParam Integer locationId) {
        String toReturn = "";
        DeleteLocationResponse response = service.deleteLocation(locationId);

        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            toReturn = "redirect:/locations";
        }

        return toReturn;
    }

    @GetMapping("/editLocation")
    public String editLocation(@RequestParam int locationId, Model model) {

        String toReturn = "";

        GetLocationResponse response = service.getLocation(locationId);
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {

            Location location = response.getToDisplay();

            model.addAttribute("location", location);
            toReturn = "editLocation";
        }

        return toReturn;
    }

    @PostMapping("/editLocation")
    public String editLocation(@ModelAttribute Location toEdit, Model model) {
        String toReturn = "";
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(toEdit);

        if (violations.isEmpty()) {
            EditLocationResponse response = service.editLocation(toEdit);
            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                toReturn = "redirect:/locationDetail?locationId=" + toEdit.getLocationId();
            }
        } else {
            model.addAttribute("locationId", toEdit.getLocationId());
            model.addAttribute("name", toEdit.getName());
            model.addAttribute("description", toEdit.getDescription());
            model.addAttribute("address", toEdit.getAddress());
            model.addAttribute("latitude", toEdit.getLatitude());
            model.addAttribute("longitude", toEdit.getLongitude());

            toReturn = "editLocation";
        }

        model.addAttribute("errors", violations);

        return toReturn;
    }
}
