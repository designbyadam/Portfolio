/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.models.AddSightingViewModel;
import com.sg.superheroSightings.models.EditSightingViewModel;
import com.sg.superheroSightings.models.ListSightingViewModel;
import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import com.sg.superheroSightings.services.AddSightingResponse;
import com.sg.superheroSightings.services.DeleteSightingResponse;
import com.sg.superheroSightings.services.EditSightingResponse;
import com.sg.superheroSightings.services.GetLocationResponse;
import com.sg.superheroSightings.services.GetSightingResponse;
import com.sg.superheroSightings.services.GetSuperResponse;
import com.sg.superheroSightings.services.ListLocationResponse;
import com.sg.superheroSightings.services.ListSightingResponse;
import com.sg.superheroSightings.services.ListSuperResponse;
import com.sg.superheroSightings.services.SuperService;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author afahrenkamp
 */
@Controller
public class SightingController {

    @Autowired
    SuperService service;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("/sightings")
    public String listSightings(Model model) {
        ListSightingViewModel vm = new ListSightingViewModel();
        String toReturn = "";

        ListSightingResponse response = service.getAllSightings();
        ListSuperResponse superResponse = service.getAllSupers();
        ListLocationResponse locationResponse = service.getAllLocations();
        List<Sighting> allSightings = response.getAllSightings();
        List<Super> allSupers = superResponse.getAllSupers();
        List<Location> allLocations = locationResponse.getAllLocations();

        if (!response.isSuccess() || !superResponse.isSuccess() || !locationResponse.isSuccess()) {
            toReturn = "error";
        } else {

            vm.setAllLocations(allLocations);
            vm.setAllSightings(allSightings);
            vm.setAllSupers(allSupers);

            model.addAttribute("vm", vm);

            toReturn = "sightings";

        }
        return toReturn;
    }

    @GetMapping("/sightingDetail")
    public String sightingDetail(Integer sightingId, Model model) {
        String toReturn = "";
        GetSightingResponse response = service.getSighting(sightingId);
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            model.addAttribute("sighting", response.getToDisplay());

            toReturn = "sightingDetail";
        }
        return toReturn;
    }

    @PostMapping("/addSighting")
    public String addSighting(AddSightingViewModel vm, Model model) {
        String toReturn = "";

        List<Super> selectedSupers = new ArrayList<Super>();
        int[] selectedIds = vm.getSelectedSupIds();
        if (selectedIds == null) {

        } else {
            for (int i = 0; i < vm.getSelectedSupIds().length; i++) {

                int superId = selectedIds[i];

                GetSuperResponse getSuperResponse = service.getSuper(superId);

                selectedSupers.add(getSuperResponse.getToDisplay());
            }
        }

        GetLocationResponse getLocResponse = service.getLocation(vm.getSelectedLocationId());
        Location locationToAdd = getLocResponse.getToDisplay();

        vm.setSighting(new Sighting());
        vm.getSighting().setAllSupers(selectedSupers);
        vm.getSighting().setSightLocation(locationToAdd);
        vm.getSighting().setLocationId(vm.getSelectedLocationId());
        vm.getSighting().setDate(vm.getDate());

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getSighting());

        if (violations.isEmpty()) {

            AddSightingResponse response = service.addSighting(vm.getSighting());

            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                toReturn = listSightings(model);
            }
        } else {
            model.addAttribute("date", vm.getSighting().getDate());
            model.addAttribute("allSupers", vm.getSighting().getAllSupers());
            model.addAttribute("locationId", vm.getSighting().getLocationId());

            toReturn = listSightings(model);
        }

        model.addAttribute("errors", violations);

        return toReturn;
    }

    @GetMapping("/editSighting")
    public String editSighting(@RequestParam int sightingId, Model model) {
        String toReturn = "";

        EditSightingViewModel vm = new EditSightingViewModel();
        GetSightingResponse response = service.getSighting(sightingId);
        Sighting toEdit = response.getToDisplay();
        ListSuperResponse listSupResponse = service.getAllSupers();
        List<Super> allSupers = listSupResponse.getAllSupers();
        ListLocationResponse listLocResponse = service.getAllLocations();
        List<Location> allLocations = listLocResponse.getAllLocations();

        if (!response.isSuccess() || !listSupResponse.isSuccess() | !listLocResponse.isSuccess()) {
            toReturn = "error";
        } else {

            vm.setSighting(toEdit);
            vm.setAllSupers(allSupers);
            vm.setAllLocations(allLocations);

            model.addAttribute("vm", vm);

            toReturn = "editSighting";

        }
        return toReturn;
    }

    @PostMapping("/editSighting")
    public String editSighting(EditSightingViewModel vm, Model model) {

        String toReturn = "";

        List<Super> selectedSupers = new ArrayList<Super>();
        int[] selectedIds = vm.getSelectedSupIds();
        if (selectedIds == null) {

        } else {
            for (int i = 0; i < vm.getSelectedSupIds().length; i++) {

                int superId = selectedIds[i];

                GetSuperResponse getSuperResponse = service.getSuper(superId);

                if (!getSuperResponse.isSuccess()) {
                    toReturn = "error";
                } else {

                    selectedSupers.add(getSuperResponse.getToDisplay());
                }
            }
        }

        GetLocationResponse locResponse = service.getLocation(vm.getSelectedLocationId());

        if (!locResponse.isSuccess()) {
            toReturn = "error";
        } else {

            vm.getSighting().setAllSupers(selectedSupers);
            vm.getSighting().setSightLocation(locResponse.getToDisplay());
            vm.getSighting().setLocationId(locResponse.getToDisplay().getLocationId());

            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            violations = validate.validate(vm.getSighting());

            if (violations.isEmpty()) {

                EditSightingResponse response = service.editSighting(vm.getSighting());

                if (!response.isSuccess()) {
                    toReturn = "error";
                } else {
                    model.addAttribute("vm", vm);

                    toReturn = "redirect:/sightingDetail?sightingId=" + vm.getSighting().getSightingId();
                }
            } else {
                model.addAttribute("vm", vm);
                toReturn = editSighting(vm.getSighting().getSightingId(), model);

            }
        }

        model.addAttribute("errors", violations);
        return toReturn;
    }

    @GetMapping("/deleteSighting")
    public String deleteSighting(@RequestParam Integer sightingId) {
        String toReturn = "";
        DeleteSightingResponse response = service.deleteSighting(sightingId);

        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            toReturn = "redirect:/sightings";
        }

        return toReturn;
    }
}
