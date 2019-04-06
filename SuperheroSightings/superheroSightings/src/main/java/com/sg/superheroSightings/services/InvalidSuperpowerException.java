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
public class InvalidSuperpowerException extends Exception {

    public InvalidSuperpowerException(String message) {
        super(message);
    }

    public InvalidSuperpowerException(String message, Throwable cause) {
        super(message, cause);
    }
}
