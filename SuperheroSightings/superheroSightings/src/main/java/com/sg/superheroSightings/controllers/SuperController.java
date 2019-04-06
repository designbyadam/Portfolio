/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.models.EditSuperViewModel;
import com.sg.superheroSightings.models.ListSuperViewModel;
import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import com.sg.superheroSightings.services.AddSuperResponse;
import com.sg.superheroSightings.services.DeleteSuperResponse;
import com.sg.superheroSightings.services.EditOrganizationResponse;
import com.sg.superheroSightings.services.EditSuperResponse;
import com.sg.superheroSightings.services.GetOrganizationResponse;
import com.sg.superheroSightings.services.GetSightingResponse;
import com.sg.superheroSightings.services.GetSuperResponse;
import com.sg.superheroSightings.services.ListOrganizationResponse;
import com.sg.superheroSightings.services.ListSightingResponse;
import com.sg.superheroSightings.services.ListSuperResponse;
import com.sg.superheroSightings.services.SuperService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author afahrenkamp
 */
@Controller
public class SuperController {

    @Autowired
    SuperService service;

    Set<ConstraintViolation<Super>> violations = new HashSet<>();

    @GetMapping("/supers")
    public String listSupers(Model model) {

        ListSuperViewModel vm = new ListSuperViewModel();
        String toReturn = "";
        ListSuperResponse supResponse = service.getAllSupers();
        ListOrganizationResponse orgResponse = service.getAllOrgs();
        ListSightingResponse sightResponse = service.getAllSightings();
        List<Super> allSupers = supResponse.getAllSupers();
        List<Organization> allOrgs = orgResponse.getAllOrgs();
        List<Sighting> allSightings = sightResponse.getAllSightings();

        if (!supResponse.isSuccess() || !orgResponse.isSuccess() || !sightResponse.isSuccess()) {
            toReturn = "error";
        } else {

            vm.setAllSupers(allSupers);
            vm.setAllOrgs(allOrgs);
            vm.setAllSightings(allSightings);

            model.addAttribute("vm", vm);
            toReturn = "supers";
        }
        return toReturn;

    }

    @GetMapping("/superDetail")
    public String superDetail(Integer superId, Model model) {
        String toReturn = "";
        GetSuperResponse response = service.getSuper(superId);
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            model.addAttribute("super", response.getToDisplay());
            toReturn = "superDetail";
        }
        return toReturn;
    }

    @PostMapping("/addSuper")
    public String addSuper(@ModelAttribute Super toAdd, Model model
    ) {
        String toReturn = "";
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(toAdd);

        if (violations.isEmpty()) {
            AddSuperResponse response = service.addSuper(toAdd);

            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                toReturn = listSupers(model);
            }
        } else {
            model.addAttribute("name", toAdd.getName());
            model.addAttribute("description", toAdd.getDescription());
            model.addAttribute("superpower", toAdd.getSuperpower());

            toReturn = listSupers(model);
        }

        model.addAttribute("errors", violations);

        return toReturn;
    }
    
    @GetMapping("/editSuper")
    public String editSuper(@RequestParam int superId, Model model) {
        String toReturn = "";
        
        EditSuperViewModel vm = new EditSuperViewModel();
        GetSuperResponse response = service.getSuper(superId);
        Super toEdit = response.getToDisplay();
        ListOrganizationResponse listOrgResponse = service.getAllOrgs();
        List<Organization> allOrgs = listOrgResponse.getAllOrgs();
        ListSightingResponse listSightResponse = service.getAllSightings();
        List<Sighting> allSightings = listSightResponse.getAllSightings();

        if (!response.isSuccess() || !listOrgResponse.isSuccess() || !listSightResponse.isSuccess()) {
            toReturn = "error";
        } else {
        vm.setSup(toEdit);
        vm.setAllOrgs(allOrgs);
        vm.setAllSightings(allSightings);
        
        model.addAttribute("vm", vm);
        
        toReturn = "editSuper";

        }
        return toReturn;
    }
    
    @PostMapping("/editSuper")
    public String editSuper(EditSuperViewModel vm, Model model) {
        
        String toReturn = "";
        
        List<Organization> selectedOrgs = new ArrayList<Organization>();
        int[] selectedOrgIds = vm.getSelectedOrgIds();
        int[] selectedSightIds = vm.getSelectedSightIds();
        if (selectedOrgIds == null || selectedSightIds == null) {
            return editSuper(vm.getSup().getSuperId(), model);
        } else {
            for (int i = 0; i < vm.getSelectedOrgIds().length; i++) {
                
                int organizationId = selectedOrgIds[i];
                
                GetOrganizationResponse getOrganizationResponse = service.getOrganization(organizationId);

                if (!getOrganizationResponse.isSuccess()) {
                    toReturn = "error";
                } else {
                selectedOrgs.add(getOrganizationResponse.getToDisplay());
                }
            }
        }
        
        List<Sighting> selectedSightings = new ArrayList<Sighting>();
        
            for (int i = 0; i < vm.getSelectedSightIds().length; i++) {
                
                int sightingId = selectedSightIds[i];
                
                GetSightingResponse getSightingResponse = service.getSighting(sightingId);

                if (!getSightingResponse.isSuccess()) {
                    toReturn = "error";
                } else {
                selectedSightings.add(getSightingResponse.getToDisplay());
                }
            }
        
        
        vm.getSup().setAllOrganizations(selectedOrgs);
        vm.getSup().setAllSightings(selectedSightings);

        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getSup());
        
        if (violations.isEmpty()) {
            
            EditSuperResponse response = service.editSuper(vm.getSup());

                if (!response.isSuccess()) {
                    toReturn = "error";
                } else {
            model.addAttribute("vm", vm);
            
            toReturn = "redirect:/superDetail?superId=" + vm.getSup().getSuperId();
                }
        } else {
            model.addAttribute("vm", vm);
            toReturn = editSuper(vm.getSup().getSuperId(), model);
            
        }
        
        model.addAttribute("errors", violations);
        return toReturn;
    }

    @GetMapping("/deleteSuper")
    public String deleteSuper(@RequestParam Integer superId) {
        String toReturn = "";
        DeleteSuperResponse response = service.deleteSuper(superId);

        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            toReturn = "redirect:/supers";
        }

        return toReturn;
    }
}
