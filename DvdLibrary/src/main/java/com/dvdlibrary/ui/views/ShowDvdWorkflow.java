/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.views;

import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.services.DvdService;
import com.dvdlibrary.services.ListDvdResponse;
import com.dvdlibrary.ui.ConsoleIO;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ShowDvdWorkflow extends Workflow {
    
    DvdService service;
    
    public ShowDvdWorkflow(DvdService service) {
        this.service = service;
    }
    
    public void run(ConsoleIO ui) {
      ListDvdResponse allDvdResponse = service.listDvds();

        if (!allDvdResponse.getSuccess()) {
            ui.print("SEVERE ERROR: " + allDvdResponse.getMessage());
        } else {

            List<Dvd> allDvds = allDvdResponse.getAllMovies();

            Dvd toDisplay = getDvdToDisplayById(ui, allDvds);
            
            displayDvdDetails(toDisplay, ui);

        }
    }

    private Dvd getDvdToDisplayById(ConsoleIO ui, List<Dvd> allMovies) {
        Dvd toDisplay = null;

        //1. get a title from the user to be removed
        //2. look through all titles for the matching id
        boolean found = false;

        while (!found) {
            int id = ui.readInt("Please enter the ID of the DVD to display: ");

            for (Dvd toCheck : allMovies) {
                found = (toCheck.getId() == id);
                if (found) {

                    toDisplay = toCheck;
                    break;
                }
            }
        } return toDisplay;

      
    }
}

