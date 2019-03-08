/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.services;

import com.dvdlibrary.dtos.Dvd;

/**
 *
 * @author afahrenkamp
 */
public class AddDvdResponse extends Response {

    Dvd movie;

    public Dvd getMovie() {
        return movie;
    }

    void setMovie(Dvd created) {
        this.movie = created;
    }
    
}
