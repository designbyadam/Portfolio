/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.services;

import com.dvdlibrary.dtos.Dvd;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListDvdResponse extends Response {

    List<Dvd> allMovies;
    
    public List<Dvd> getAllMovies() {
        return allMovies;
    }

    void setAllMovies(List<Dvd> allMovies) {
        this.allMovies = allMovies;
    }
    
}
