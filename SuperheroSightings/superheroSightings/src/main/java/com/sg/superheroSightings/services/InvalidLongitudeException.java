/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

/**
 *
 * @author afahrenkamp
 */
public class InvalidLongitudeException extends Exception {

    public InvalidLongitudeException(String message) {
        super(message);
    }

    public InvalidLongitudeException(String message, Throwable cause) {
        super(message, cause);
    }
}
