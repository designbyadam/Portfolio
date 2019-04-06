/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author afahrenkamp
 */
public class Sighting {

    private Integer sightingId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Date must be in the past.")
    @NotNull(message = "You must enter a date.")
    private LocalDate date;

    @NotEmpty(message="You must select at least one super.") 
    @Valid
    private List<Super> allSupers = new ArrayList<Super>();

    @NotNull
    private Location sightLocation;

    private Integer locationId;

    private String dateString;

    
    public boolean hasSup( int supId) {
        boolean found = false;
        for (Super toCheck : allSupers) {
            if (toCheck.getSuperId() == supId) {
                found = true;
                break;
            }
        }
        return found;
    }
    /**
     * @return the sightingId
     */
    public Integer getSightingId() {
        return sightingId;
    }

    /**
     * @param sightingId the sightingId to set
     */
    public void setSightingId(Integer sightingId) {
        this.sightingId = sightingId;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the allSupers
     */
    public List<Super> getAllSupers() {
        return allSupers;
    }

    /**
     * @param allSupers the allSupers to set
     */
    public void setAllSupers(List<Super> allSupers) {
        this.allSupers = allSupers;
    }

    /**
     * @return the sightLocation
     */
    public Location getSightLocation() {
        return sightLocation;
    }

    /**
     * @param sightLocation the sightLocation to set
     */
    public void setSightLocation(Location sightLocation) {
        this.sightLocation = sightLocation;
    }

    /**
     * @return the locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return formatter.format(getDate());
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.getSightingId();
        hash = 83 * hash + Objects.hashCode(this.getDate());
        hash = 83 * hash + Objects.hashCode(this.getAllSupers());
        hash = 83 * hash + Objects.hashCode(this.getSightLocation());
        hash = 83 * hash + this.getLocationId();
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
        final Sighting other = (Sighting) obj;
        if (this.getSightingId() != other.getSightingId()) {
            return false;
        }
        if (this.getLocationId() != other.getLocationId()) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.allSupers, other.allSupers)) {
            return false;
        }
        if (!Objects.equals(this.sightLocation, other.sightLocation)) {
            return false;
        }
        return true;
    }

}
