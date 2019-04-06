/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.daos.LocationDao;
import com.sg.superheroSightings.daos.LocationPersistenceException;
import com.sg.superheroSightings.daos.OrganizationDao;
import com.sg.superheroSightings.daos.OrganizationPersistenceException;
import com.sg.superheroSightings.daos.SightingDao;
import com.sg.superheroSightings.daos.SightingPersistenceException;
import com.sg.superheroSightings.daos.SuperDao;
import com.sg.superheroSightings.daos.SuperPersistenceException;
import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author afahrenkamp
 */
@Service
public class SuperService {

    @Autowired
    SuperDao superDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;

    public SuperService(SuperDao superDao, LocationDao locationDao, OrganizationDao orgDao, SightingDao sightingDao) {
        this.superDao = superDao;
        this.locationDao = locationDao;
        this.orgDao = orgDao;
        this.sightingDao = sightingDao;
    }

    public SuperService() {

    }

    public ListSuperResponse getAllSupers() {
        ListSuperResponse response = new ListSuperResponse();
        List<Super> allSupers = new ArrayList();

        try {
            allSupers = superDao.getAllSupers();

            response.setSuccess(true);
            response.setAllSupers(allSupers);

        } catch (SuperPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public GetSuperResponse getSuper(int superId) {
        GetSuperResponse response = new GetSuperResponse();
        try {
            List<Super> allSupers = superDao.getAllSupers();
            validateSuperId(superId, allSupers);
            List<Sighting> allSightings = sightingDao.getSightingsBySuperId(superId);
            List<Sighting> sightingsWithLocation = new ArrayList();
            for (Sighting toCheck : allSightings) {

                int currentLocationId = toCheck.getLocationId();
                Location toAdd = locationDao.getLocationById(currentLocationId);
                toCheck.setSightLocation(toAdd);
                sightingsWithLocation.add(toCheck);
            }

            List<Organization> allOrgs = orgDao.getOrgsBySuperId(superId);

            Super toDisplay = superDao.getSuperById(superId);
            toDisplay.setAllSightings(sightingsWithLocation);
            toDisplay.setAllOrganizations(allOrgs);

            response.setToDisplay(toDisplay);
            response.setSuccess(true);
        } catch (LocationPersistenceException | SuperPersistenceException
                | SightingPersistenceException | OrganizationPersistenceException
                | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

    public AddSuperResponse addSuper(Super toAdd) {

        AddSuperResponse response = new AddSuperResponse();

        try {
            validateSuperName(toAdd);
            validateSuperDescription(toAdd);
            validateSuperSuperpower(toAdd);

            Super superToAdd = superDao.addSuper(toAdd);

            response.setToAdd(superToAdd);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException
                | InvalidSuperpowerException | SuperPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;

    }
    
    
    public EditSuperResponse editSuper(Super toEdit) {
        EditSuperResponse response = new EditSuperResponse();
        try {
            List<Super> allSupers = superDao.getAllSupers();
            validateSuperOrganizations(toEdit.getAllOrganizations());
            validateSuperSightings(toEdit.getAllSightings());
            validateSuperName(toEdit);
            validateSuperDescription(toEdit);
            validateSuperSuperpower(toEdit);
            validateSuperId(toEdit.getSuperId(), allSupers);
            Super supToEdit = superDao.updateSuper(toEdit);
            supToEdit.setAllSightings(sightingDao.getSightingsBySuperId(supToEdit.getSuperId()));
            supToEdit.setAllOrganizations(orgDao.getOrgsBySuperId(supToEdit.getSuperId()));

            response.setEditedSup(supToEdit);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException 
                | OrganizationPersistenceException 
                | SuperPersistenceException | InvalidSightingsException
                | InvalidIdException | InvalidOrganizationsException 
                | InvalidSuperpowerException | SightingPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public DeleteSuperResponse deleteSuper(int superId) {
        DeleteSuperResponse response = new DeleteSuperResponse();
        try {

            List<Super> allSupers = superDao.getAllSupers();
            validateSuperId(superId, allSupers);

            superDao.deleteSuperById(superId);

            response.setSuccess(true);

        } catch (SuperPersistenceException | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public ListSightingResponse getAllSightings() {
        ListSightingResponse response = new ListSightingResponse();
        List<Sighting> allSightings = new ArrayList();
        try {
            allSightings = sightingDao.getAllSightings();

            response.setSuccess(true);
            response.setAllSightings(allSightings);

        } catch (SightingPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public ListOrganizationResponse getAllOrgs() {

        ListOrganizationResponse response = new ListOrganizationResponse();
        List<Organization> allOrgs = new ArrayList();
        try {
            allOrgs = orgDao.getAllOrgs();

            response.setSuccess(true);
            response.setAllOrgs(allOrgs);

        } catch (OrganizationPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public GetOrganizationResponse getOrganization(int organizationId) {

        GetOrganizationResponse response = new GetOrganizationResponse();
        Organization toDisplay = new Organization();
        try {
            List<Organization> allOrgs = orgDao.getAllOrgs();
            validateOrgId(organizationId, allOrgs);

            toDisplay = orgDao.getOrgById(organizationId);
            toDisplay.setAllSupers(superDao.getSupersByOrgId(organizationId));

            response.setToDisplay(toDisplay);
            response.setSuccess(true);

        } catch (OrganizationPersistenceException | SuperPersistenceException
                | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
    
    public AddOrganizationResponse addOrganization(Organization toAdd) {
        AddOrganizationResponse response = new AddOrganizationResponse();

        try {
            validateOrgName(toAdd);
            validateOrgDescription(toAdd);
            validateOrgAddress(toAdd);
            validateOrgPhoneNumber(toAdd);
            validateOrgEmail(toAdd);
            Organization organizationToAdd = orgDao.addOrg(toAdd);

            response.setToAdd(organizationToAdd);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException
                | InvalidAddressException | InvalidPhoneNumberException
                | InvalidEmailException | OrganizationPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }
    
       public EditOrganizationResponse editOrganization(Organization toEdit) {
        EditOrganizationResponse response = new EditOrganizationResponse();
        try {
            List<Organization> allOrgs = orgDao.getAllOrgs();
            validateOrganizationSupers(toEdit.getAllSupers());
            validateOrgName(toEdit);
            validateOrgDescription(toEdit);
            validateOrgAddress(toEdit);
            validateOrgPhoneNumber(toEdit);
            validateOrgEmail(toEdit);
            validateOrgId(toEdit.getOrganizationId(), allOrgs);
            Organization orgToEdit = orgDao.updateOrg(toEdit);
            orgToEdit.setAllSupers(superDao.getSupersByOrgId(orgToEdit.getOrganizationId()));

            response.setEditedOrganization(orgToEdit);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException
                | InvalidAddressException | InvalidPhoneNumberException
                | InvalidEmailException | OrganizationPersistenceException 
                | SuperPersistenceException | InvalidSupersException
                | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public DeleteOrganizationResponse deleteOrganization(Integer organizationId) {
        DeleteOrganizationResponse response = new DeleteOrganizationResponse();
        try {

            List<Organization> allOrgs = orgDao.getAllOrgs();
            validateOrgId(organizationId, allOrgs);

            orgDao.deleteOrgById(organizationId);

            response.setSuccess(true);

        } catch (OrganizationPersistenceException | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public ListLocationResponse getAllLocations() {
        ListLocationResponse response = new ListLocationResponse();
        try {
            List<Location> allLocations = locationDao.getAllLocations();

            response.setSuccess(true);
            response.setAllLocations(allLocations);

        } catch (LocationPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public GetSightingResponse getSighting(int sightingId) {
        GetSightingResponse response = new GetSightingResponse();
        try {

            List<Sighting> allSightings = sightingDao.getAllSightings();
            validateSightingId(sightingId, allSightings);

            Sighting toDisplay = sightingDao.getSightingById(sightingId);
            int currentLocationId = toDisplay.getLocationId();
            Location currentLocation = locationDao.getLocationById(currentLocationId);
            toDisplay.setAllSupers(superDao.getAllSupersBySightingId(sightingId));
            toDisplay.setSightLocation(currentLocation);

            response.setToDisplay(toDisplay);
            response.setSuccess(true);

        } catch (LocationPersistenceException | SightingPersistenceException
                | SuperPersistenceException | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public AddSightingResponse addSighting(Sighting toAdd) {
        AddSightingResponse response = new AddSightingResponse();
        try {
            
            List<Location> allLocations = locationDao.getAllLocations();
            
            validateSightingSupers(toAdd.getAllSupers());
            validateSightingDate(toAdd.getDate());
            validateSightingLocation(toAdd.getSightLocation());
            validateLocationId(toAdd.getLocationId(), allLocations);
            
            Sighting sightingToAdd = sightingDao.addSighting(toAdd);

            response.setToAdd(sightingToAdd);
            response.setSuccess(true);

        } catch (InvalidDateException | InvalidSupersException
                | InvalidLocationException | SightingPersistenceException
                | LocationPersistenceException 
                | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public EditSightingResponse editSighting(Sighting toEdit) {
        EditSightingResponse response = new EditSightingResponse();
        try {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            List<Location> allLocations = locationDao.getAllLocations();
            validateSightingSupers(toEdit.getAllSupers());
            validateSightingDate(toEdit.getDate());
            validateSightingLocation(toEdit.getSightLocation());
            validateSightingId(toEdit.getSightingId(), allSightings);
            Sighting sightingToEdit = sightingDao.updateSighting(toEdit);
            Location currentLocation = locationDao.getLocationById(sightingToEdit.getLocationId());
            sightingToEdit.setAllSupers(superDao.getAllSupersBySightingId(sightingToEdit.getSightingId()));
            sightingToEdit.setSightLocation(currentLocation);

            response.setEditedSighting(sightingToEdit);
            response.setSuccess(true);

        } catch (InvalidDateException | InvalidSupersException
                | InvalidLocationException | SightingPersistenceException
                | LocationPersistenceException | SuperPersistenceException
                | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }
    
    
    public DeleteSightingResponse deleteSighting(int sightingId) {
        DeleteSightingResponse response = new DeleteSightingResponse();
        try {

            List<Sighting> allSightings = sightingDao.getAllSightings();
            validateSightingId(sightingId, allSightings);

            Sighting toDelete = sightingDao.getSightingById(sightingId);

            sightingDao.deleteSightingById(sightingId);

            response.setSuccess(true);

        } catch (InvalidIdException
                | SightingPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public GetLocationResponse getLocation(int locationId) {
        GetLocationResponse response = new GetLocationResponse();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(locationId, allLocations);

            Location toDisplay = locationDao.getLocationById(locationId);

            List<Sighting> allSightingsWithoutSuper = sightingDao.getSightingsByLocationId(locationId);
            List<Sighting> sightingsWithSuper = new ArrayList();
            for (Sighting toCheck : allSightingsWithoutSuper) {
                toCheck.setAllSupers(superDao.getAllSupersBySightingId(toCheck.getSightingId()));
                sightingsWithSuper.add(toCheck);
            }

            toDisplay.setAllSightings(sightingsWithSuper);

            response.setToDisplay(toDisplay);
            response.setSuccess(true);

        } catch (LocationPersistenceException | SuperPersistenceException
                | SightingPersistenceException | InvalidIdException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public AddLocationResponse addLocation(Location toAdd) {
        AddLocationResponse response = new AddLocationResponse();

        try {
            validateLocationName(toAdd);
            validateLocationDescription(toAdd);
            validateLocationAddress(toAdd);
            validateLocationCoordinates(toAdd);
            Location locationToAdd = locationDao.addLocation(toAdd);

            response.setToAdd(locationToAdd);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException
                | InvalidAddressException | InvalidLatitudeException
                | InvalidLongitudeException | LocationPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public DeleteLocationResponse deleteLocation(int locationId) {
        DeleteLocationResponse response = new DeleteLocationResponse();
        try {

            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(locationId, allLocations);

            Location toDelete = locationDao.getLocationById(locationId);

            List<Sighting> allSightings = sightingDao.getSightingsByLocationId(locationId);
            for (int i = 0; i < allSightings.size(); i++) {
                Sighting toCheck = allSightings.get(i);

                sightingDao.deleteSightingById(toCheck.getSightingId());

            }
            locationDao.deleteLocationById(locationId);

            response.setSuccess(true);

        } catch (LocationPersistenceException | InvalidIdException
                | SightingPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public EditLocationResponse editLocation(Location toEdit) {
        EditLocationResponse response = new EditLocationResponse();
        try {
            List<Location> allLocations = locationDao.getAllLocations();
            validateLocationId(toEdit.getLocationId(), allLocations);
            validateLocationName(toEdit);
            validateLocationDescription(toEdit);
            validateLocationAddress(toEdit);
            validateLocationCoordinates(toEdit);
            Location locationToEdit = locationDao.updateLocation(toEdit);

            response.setEditedLocation(locationToEdit);
            response.setSuccess(true);

        } catch (InvalidNameException | InvalidDescriptionException
                | InvalidAddressException | InvalidLatitudeException
                | InvalidLongitudeException | LocationPersistenceException
                | InvalidIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public ListSightingResponse getLastTenSightings() {
        ListSightingResponse response = new ListSightingResponse();
        List<Sighting> allSightings = new ArrayList();
        List<Sighting> allSightingsHydrated = new ArrayList();
        try {
            allSightings = sightingDao.getAllSightings();

            for (int i = 0; i < allSightings.size(); i++) {
                Sighting toDisplay = sightingDao.getSightingById(allSightings.get(i).getSightingId());
                int currentLocationId = toDisplay.getLocationId();
                Location currentLocation = locationDao.getLocationById(currentLocationId);
                toDisplay.setAllSupers(superDao.getAllSupersBySightingId(allSightings.get(i).getSightingId()));
                toDisplay.setSightLocation(currentLocation);

                allSightingsHydrated.add(toDisplay);
            }
            List<Sighting> lastTenSightings = getLastTenSightings(allSightingsHydrated);

            response.setSuccess(true);
            response.setAllSightings(lastTenSightings);

        } catch (SightingPersistenceException | LocationPersistenceException | SuperPersistenceException ex) {
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    private void validateLocationName(Location toValidate) throws InvalidNameException {

        if (toValidate.getName().equals("")) {
            throw new InvalidNameException("Name cannot be blank.");
        }

        if (toValidate.getName().length() > 30) {
            throw new InvalidNameException("Name cannot be greater than 30 characters.");
        }

    }

    private void validateLocationDescription(Location toValidate) throws InvalidDescriptionException {
        if (toValidate.getDescription().equals("")) {
            throw new InvalidDescriptionException("Description cannot be blank.");
        }

        if (toValidate.getDescription().length() > 50) {
            throw new InvalidDescriptionException("Description cannot be greater than 50 characters.");
        }
    }

    private void validateLocationAddress(Location toValidate) throws InvalidAddressException {
        if (toValidate.getAddress().equals("")) {
            throw new InvalidAddressException("Address cannot be blank.");
        }

        if (toValidate.getAddress().length() > 100) {
            throw new InvalidAddressException("Address cannot be greater than 100 characters.");
        }

    }

    private void validateLocationCoordinates(Location toValidate) throws InvalidLongitudeException, InvalidLatitudeException {
        if ((toValidate.getLatitude() == null)) {
            throw new InvalidLatitudeException("Latitude cannot be blank.");
        }

        if ((toValidate.getLatitude() > 90) || (toValidate.getLatitude() < -90)) {
            throw new InvalidLatitudeException("Latitude must be between -90.000000 and 90.000000.");
        }

        if ((toValidate.getLongitude() == null)) {
            throw new InvalidLongitudeException("Longitude cannot be blank.");
        }

        if ((toValidate.getLongitude() > 180) || (toValidate.getLongitude() < -180)) {
            throw new InvalidLongitudeException("Latitude must be between -180.000000 and 180.000000.");
        }

    }

    private void validateLocationId(int locationId, List<Location> allLocations) throws InvalidIdException {

        boolean isValid = false;
        for (Location toCheck : allLocations) {
            if (toCheck.getLocationId() == locationId) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidIdException("Location ID is invalid.");
        }
    }

    private void validateSuperId(int superId, List<Super> allSupers) throws InvalidIdException {
        boolean isValid = false;
        for (Super toCheck : allSupers) {
            if (toCheck.getSuperId() == superId) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidIdException("Super ID is invalid.");
        }
    }

    private void validateOrgId(int organizationId, List<Organization> allOrgs) throws InvalidIdException {
        boolean isValid = false;
        for (Organization toCheck : allOrgs) {
            if (toCheck.getOrganizationId() == organizationId) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidIdException("Organization ID is invalid.");
        }
    }

    private void validateSightingId(int sightingId, List<Sighting> allSightings) throws InvalidIdException {
        boolean isValid = false;
        for (Sighting toCheck : allSightings) {
            if (toCheck.getSightingId() == sightingId) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidIdException("Sighting ID is invalid.");
        }
    }

    private void validateOrgName(Organization toValidate) throws InvalidNameException {
        if (toValidate.getName().equals("")) {
            throw new InvalidNameException("Name cannot be blank.");
        }

        if (toValidate.getName().length() > 30) {
            throw new InvalidNameException("Name cannot be greater than 30 characters.");
        }
    }

    private void validateOrgDescription(Organization toValidate) throws InvalidDescriptionException {
        if (toValidate.getDescription().equals("")) {
            throw new InvalidDescriptionException("Description cannot be blank.");
        }

        if (toValidate.getDescription().length() > 50) {
            throw new InvalidDescriptionException("Description cannot be greater than 50 characters.");
        }
    }

    private void validateOrgAddress(Organization toValidate) throws InvalidAddressException {
        if (toValidate.getAddress().equals("")) {
            throw new InvalidAddressException("Address cannot be blank.");
        }

        if (toValidate.getAddress().length() > 100) {
            throw new InvalidAddressException("Address cannot be greater than 100 characters.");
        }
    }

    private void validateOrgPhoneNumber(Organization toValidate) throws InvalidPhoneNumberException {
        if (toValidate.getPhoneNumber().equals("")) {
            throw new InvalidPhoneNumberException("Phone number cannot be blank.");
        }

        if (toValidate.getPhoneNumber().length() < 7) {
            throw new InvalidPhoneNumberException("Phone number must be at least 7 digits.");
        }

        if (toValidate.getPhoneNumber().length() > 14) {
            throw new InvalidPhoneNumberException("Phone number cannot be greater than 14 digits.");
        }

    }

    private void validateOrgEmail(Organization toValidate) throws InvalidEmailException {
        if (toValidate.getEmail().equals("")) {
            throw new InvalidEmailException("Email cannot be blank.");
        }

        if (toValidate.getEmail().length() < 3) {
            throw new InvalidEmailException("Email must be at least 3 characters.");
        }

        if (toValidate.getEmail().length() > 35) {
            throw new InvalidEmailException("Address cannot be greater than 35 characters.");
        }
    }

    private List<Sighting> getLastTenSightings(List<Sighting> allSightings) {
        List<Sighting> toReturn = new ArrayList();
        List<Sighting> allSightingsOrdered = allSightings.stream()
                .sorted(Comparator.comparing(Sighting::getDate).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < allSightingsOrdered.size(); i++) {
            if (i <= 9) {

                toReturn.add(allSightingsOrdered.get(i));
            }
        }
        return toReturn;
    }

    private void validateSuperName(Super toValidate) throws InvalidNameException {
        if (toValidate.getName().equals("")) {
            throw new InvalidNameException("Name cannot be blank.");
        }

        if (toValidate.getName().length() > 30) {
            throw new InvalidNameException("Name cannot be greater than 30 characters.");
        }
    }

    private void validateSuperDescription(Super toValidate) throws InvalidDescriptionException {
        if (toValidate.getDescription().equals("")) {
            throw new InvalidDescriptionException("Description cannot be blank.");
        }

        if (toValidate.getDescription().length() > 50) {
            throw new InvalidDescriptionException("Description cannot be greater than 50 characters.");
        }
    }

    private void validateSuperSuperpower(Super toValidate) throws InvalidSuperpowerException {
        if (toValidate.getSuperpower().equals("")) {
            throw new InvalidSuperpowerException("Superpower cannot be blank.");
        }

        if (toValidate.getSuperpower().length() > 100) {
            throw new InvalidSuperpowerException("Superpower cannot be greater than 100 characters.");
        }
    }

    private void validateSightingSupers(List<Super> allSupers) throws InvalidSupersException {
        if (allSupers == null || allSupers.isEmpty()) {
            throw new InvalidSupersException("At least one super must be selected.");
        }
    }

    private void validateSightingDate(LocalDate date) throws InvalidDateException {
        LocalDate today = LocalDate.now();
        
        if (date == null || date.isAfter(today)) {
            throw new InvalidDateException("Date must be before today.");
        }
    }

    private void validateSightingLocation(Location sightLocation) throws InvalidLocationException {
                if (sightLocation == null) {
            throw new InvalidLocationException("A location must be selected.");
        }
    }

    private void validateOrganizationSupers(List<Super> allSupers) throws InvalidSupersException {
        if (allSupers == null || allSupers.isEmpty()) {
            throw new InvalidSupersException("At least one super must be selected.");
        }
    }

    private void validateSuperOrganizations(List<Organization> allOrganizations) throws InvalidOrganizationsException {
        if (allOrganizations == null || allOrganizations.isEmpty()) {
            throw new InvalidOrganizationsException("At least one organization must be selected.");
        }
    }

    private void validateSuperSightings(List<Sighting> allSightings) throws InvalidSightingsException {
        if (allSightings == null || allSightings.isEmpty()) {
            throw new InvalidSightingsException("At least one sighting must be selected.");
        }
    }

}
