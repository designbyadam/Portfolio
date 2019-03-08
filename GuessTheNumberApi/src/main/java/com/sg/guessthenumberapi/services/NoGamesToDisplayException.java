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
public class NoGamesToDisplayException extends Exception {

    public NoGamesToDisplayException(String message) {
        super(message);
    }

    public NoGamesToDisplayException(String message, Throwable cause) {
        super(message, cause);
    }
}
