/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

/**
 *
 * @author afahrenkamp
 */
public class InvalidItemException extends Exception {

    public InvalidItemException(String message) {
        super(message);
    }

    public InvalidItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
