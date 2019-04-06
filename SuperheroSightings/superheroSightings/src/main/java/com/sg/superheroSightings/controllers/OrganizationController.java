/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.models.EditOrgViewModel;
import com.sg.superheroSightings.models.ListOrgViewModel;
import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Super;
import com.sg.superheroSightings.services.AddOrganizationResponse;
import com.sg.superheroSightings.services.DeleteOrganizationResponse;
import com.sg.superheroSightings.services.EditOrganizationResponse;
import com.sg.superheroSightings.services.GetOrganizationResponse;
import com.sg.superheroSightings.services.GetSuperResponse;
import com.sg.superheroSightings.services.ListOrganizationResponse;
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
public class OrganizationController {

    @Autowired
    SuperService service;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("/organizations")
    public String listOrganizations(Model model) {
        ListOrgViewModel vm = new ListOrgViewModel();
        String toReturn = "";
        ListOrganizationResponse response = service.getAllOrgs();
        ListSuperResponse superResponse = service.getAllSupers();
        List<Organization> allOrgs = response.getAllOrgs();
        List<Super> allSupers = superResponse.getAllSupers();

        if (!response.isSuccess() || !superResponse.isSuccess()) {
            toReturn = "error";
        } else {
            vm.setAllOrgs(allOrgs);
            vm.setAllSupers(allSupers);

            model.addAttribute("vm", vm);
            toReturn = "organizations";
        }

        return toReturn;

    }

    @GetMapping("/organizationDetail")
    public String organizationDetail(Integer organizationId, Model model) {
        String toReturn = "";

        GetOrganizationResponse response = service.getOrganization(organizationId);
        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            model.addAttribute("organization", response.getToDisplay());
            toReturn = "organizationDetail";
        }
        return toReturn;
    }

    @PostMapping("/addOrganization")
    public String addOrganization(@ModelAttribute Organization toAdd, Model model) {
        String toReturn = "";
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(toAdd);

        if (violations.isEmpty()) {
            AddOrganizationResponse response = service.addOrganization(toAdd);

            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                toReturn = listOrganizations(model);
            }
        } else {
            model.addAttribute("name", toAdd.getName());
            model.addAttribute("description", toAdd.getDescription());
            model.addAttribute("address", toAdd.getAddress());
            model.addAttribute("phoneNumber", toAdd.getPhoneNumber());
            model.addAttribute("email", toAdd.getEmail());

            toReturn = listOrganizations(model);
        }

        model.addAttribute("errors", violations);

        return toReturn;
    }

    @GetMapping("/editOrganization")
    public String editOrganization(@RequestParam int organizationId, Model model) {
        String toReturn = "";

        EditOrgViewModel vm = new EditOrgViewModel();
        GetOrganizationResponse response = service.getOrganization(organizationId);
        Organization toEdit = response.getToDisplay();
        ListSuperResponse listSupResponse = service.getAllSupers();
        List<Super> allSupers = listSupResponse.getAllSupers();

        if (!response.isSuccess() || !listSupResponse.isSuccess()) {
            toReturn = "error";
        } else {
            vm.setOrganization(toEdit);
            vm.setAllSupers(allSupers);

            model.addAttribute("vm", vm);

            toReturn = "editOrganization";

        }
        return toReturn;
    }

    @PostMapping("/editOrganization")
    public String editOrganization(EditOrgViewModel vm, Model model) {

        String toReturn = "";

        List<Super> selectedSupers = new ArrayList<Super>();
        int[] selectedIds = vm.getSelectedSupIds();
        if (selectedIds == null) {
            return editOrganization(vm.getOrganization().getOrganizationId(), model);
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
            vm.getOrganization().setAllSupers(selectedSupers);
            vm.setAllSupers(selectedSupers);
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(vm.getOrganization());

        if (violations.isEmpty()) {

            EditOrganizationResponse response = service.editOrganization(vm.getOrganization());

            if (!response.isSuccess()) {
                toReturn = "error";
            } else {
                model.addAttribute("vm", vm);

                toReturn = "redirect:/organizationDetail?organizationId=" + vm.getOrganization().getOrganizationId();
            }
        } else {
            model.addAttribute("vm", vm);
            toReturn = editOrganization(vm.getOrganization().getOrganizationId(), model);

        }

        model.addAttribute("errors", violations);
        return toReturn;
    }

    @GetMapping("/deleteOrganization")
    public String deleteOrganization(@RequestParam Integer organizationId) {
        String toReturn = "";
        DeleteOrganizationResponse response = service.deleteOrganization(organizationId);

        if (!response.isSuccess()) {
            toReturn = "error";
        } else {
            toReturn = "redirect:/organizations";
        }

        return toReturn;
    }

}
