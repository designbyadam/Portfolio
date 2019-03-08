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
public class EditDvdResponse extends Response {

    Dvd editedMovie;
    
    public Dvd getEditedMovie() {
        return editedMovie;
    }

    void setEditedMovie(Dvd editedMovie) {
        this.editedMovie = editedMovie;
    }
    
}
