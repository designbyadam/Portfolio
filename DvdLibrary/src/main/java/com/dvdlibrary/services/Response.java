/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.services;

/**
 *
 * @author afahrenkamp
 */
public abstract class Response {

    boolean success = false;
    String message;

    public boolean getSuccess() {
        return success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
     }

    void setMessage(String message) {
        this.message = message;
    }
}
