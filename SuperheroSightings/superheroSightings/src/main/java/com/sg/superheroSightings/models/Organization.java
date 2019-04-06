/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author afahrenkamp
 */
public class Organization {

    private Integer organizationId;

    @NotBlank(message = "Name must not be blank.")
    @Size(max = 30, message = "Name cannot be more than 30 characters.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 50, message = "Description cannot be more than 50 characters.")
    private String description;

    @NotBlank(message = "Address must not be blank.")
    @Size(max = 100, message = "Address cannot be more than 100 characters.")
    private String address;

    @NotBlank(message = "Phone number must not be blank.")
    @Size(max = 14, message = "Phone number cannot be more than 14 digits.")
    @Size(min = 7, message = "Phone number must be at least 7 digits.")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank.")
    @Size(max = 35, message = "Email cannot be more than 35 characters.")
    @Size(min = 3, message = "Email must be at least 3 characters.")
    private String email;

    private List<Super> allSupers = new ArrayList<Super>();
    
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
     * @return the organizationId
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
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
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.organizationId;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.address);
        hash = 79 * hash + Objects.hashCode(this.phoneNumber);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.allSupers);
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
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
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
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.allSupers, other.allSupers)) {
            return false;
        }
        return true;
    }

}
