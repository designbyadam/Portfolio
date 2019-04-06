/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author afahrenkamp
 */
public class Location {

    private Integer locationId;

    @NotBlank(message = "Name must not be blank.")
    @Size(max = 30, message = "Name cannot be more than 30 characters.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 50, message = "Description cannot be more than 50 characters.")
    private String description;

    @NotBlank(message = "Address must not be blank.")
    @Size(max = 100, message = "Address cannot be more than 100 characters.")
    private String address;
    
    @NotNull(message = "Latitude must not be blank.")
    @DecimalMin(value = "-90.000000", message = "Latitude must be greater than -90.000000")
    @DecimalMax(value = "90.000000", message = "Latitude must be less than 90.000000")
    private Double latitude;
    
    @NotNull(message = "Longitude must not be blank.")
    @DecimalMin(value = "-120.000000", message = "Longitude must be greater than -120.000000")
    @DecimalMax(value = "120.000000", message = "Longitude must be less than 120.000000")
    private Double longitude;
    
    private List<Sighting> allSightings = new ArrayList<Sighting>();

    /**
     * @return the locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.locationId;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.allSightings);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.allSightings, other.allSightings)) {
            return false;
        }
        return true;
    }
    
    

}
