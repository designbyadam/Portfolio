/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author afahrenkamp
 */
public class Super {

    private Integer superId;

    @NotBlank(message = "Name must not be blank.")
    @Size(max = 30, message = "Name cannot be more than 30 characters.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 50, message = "Description cannot be more than 30 characters.")
    private String description;

    @NotBlank(message = "Superpower must not be blank.")
    @Size(max = 100, message = "Superpower cannot be more than 30 characters.")
    private String superpower;

    private List<Sighting> allSightings = new ArrayList<Sighting>();

    private List<Organization> allOrganizations = new ArrayList<Organization>();
    
    public boolean hasOrg( int orgId) {
        boolean found = false;
        for (Organization toCheck : allOrganizations) {
            if (toCheck.getOrganizationId() == orgId) {
                found = true;
                break;
            }
        }
        return found;
    }
    
    public boolean hasSighting( int sightingId) {
        boolean found = false;
        for (Sighting toCheck : allSightings) {
            if (toCheck.getSightingId() == sightingId) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * @return the superId
     */
    public Integer getSuperId() {
        return superId;
    }

    /**
     * @param superId the superId to set
     */
    public void setSuperId(Integer superId) {
        this.superId = superId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the superpower
     */
    public String getSuperpower() {
        return superpower;
    }

    /**
     * @param superpower the superpower to set
     */
    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    /**
     * @return the allSightings
     */
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    /**
     * @param allSightings the allSightings to set
     */
    public void setAllSightings(List<Sighting> allSightings) {
        this.allSightings = allSightings;
    }

    /**
     * @return the allOrganizations
     */
    public List<Organization> getAllOrganizations() {
        return allOrganizations;
    }

    /**
     * @param allOrganizations the allOrganizations to set
     */
    public void setAllOrganizations(List<Organization> allOrganizations) {
        this.allOrganizations = allOrganizations;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.superId;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.superpower);
        hash = 43 * hash + Objects.hashCode(this.getAllSightings());
        hash = 43 * hash + Objects.hashCode(this.getAllOrganizations());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Super other = (Super) obj;
        if (this.superId != other.superId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Objects.equals(this.allSightings, other.allSightings)) {
            return false;
        }
        if (!Objects.equals(this.allOrganizations, other.allOrganizations)) {
            return false;
        }
        return true;
    }

}
