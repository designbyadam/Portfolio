/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

/**
 *
 * @author afahrenkamp
 */
public class OrganizationPersistenceException extends Exception {

    public OrganizationPersistenceException(String message) {
        super(message);
    }

    public OrganizationPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
