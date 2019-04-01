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
public class NoRoundsToDisplayException extends Exception {

    public NoRoundsToDisplayException(String message) {
        super(message);
    }

    public NoRoundsToDisplayException(String message, Throwable cause) {
        super(message, cause);
    }
}
