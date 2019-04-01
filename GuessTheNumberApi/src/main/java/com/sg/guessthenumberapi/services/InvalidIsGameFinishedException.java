/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

/**
 *
 * @author afahrenkamp
 */
public class InvalidIsGameFinishedException extends Exception {
    
    public InvalidIsGameFinishedException(String message) {
        super(message);
    }

    public InvalidIsGameFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
