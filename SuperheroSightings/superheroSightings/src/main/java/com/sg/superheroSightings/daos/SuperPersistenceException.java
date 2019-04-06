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
public class SuperPersistenceException extends Exception {

    public SuperPersistenceException(String message) {
        super(message);
    }

    public SuperPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
