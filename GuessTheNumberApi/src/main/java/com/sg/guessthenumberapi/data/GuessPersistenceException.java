/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

/**
 *
 * @author afahrenkamp
 */
public class GuessPersistenceException extends Exception {

    public GuessPersistenceException(String message) {
        super(message);
    }

    public GuessPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
