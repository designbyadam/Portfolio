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
public class InvalidSupersException extends Exception {
     public InvalidSupersException(String message) {
        super(message);
    }

    public InvalidSupersException(String message, Throwable cause) {
        super(message, cause);
    }
}
