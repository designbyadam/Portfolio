/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.views;

import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.ui.ConsoleIO;

/**
 *
 * @author afahrenkamp
 */
public abstract class Workflow {
    protected void displayDvdDetails(Dvd movie, ConsoleIO ui) {
        
        ui.print("\n" + movie.getTitle() + "\n" );
        ui.print( "ID: " + movie.getId()+ "\n" );
        ui.print( "Year: " + movie.getReleaseYear()+ "\n" );
        ui.print( "MPAA: " + movie.getRating()+ "\n" );
        ui.print( "Director: " + movie.getDirector()+ "\n" );
        ui.print( "Studio: " + movie.getStudio()+ "\n" );
        ui.print(movie.getNote()+ "\n" );
    }
    
}
