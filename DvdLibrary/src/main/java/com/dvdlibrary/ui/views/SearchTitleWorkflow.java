/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.views;

import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.services.DvdService;
import com.dvdlibrary.services.ListDvdResponse;
import com.dvdlibrary.services.RemoveDvdResponse;
import com.dvdlibrary.ui.ConsoleIO;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class SearchTitleWorkflow extends Workflow {

    DvdService service;

    public SearchTitleWorkflow(DvdService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {
        ListDvdResponse allDvdResponse = service.listDvds();

        if (!allDvdResponse.getSuccess()) {
            ui.print("SEVERE ERROR: " + allDvdResponse.getMessage());
        } else {

            List<Dvd> allDvds = allDvdResponse.getAllMovies();

            Dvd toDisplay = getDvdToDisplayByTitle(ui, allDvds);
            
            displayDvdDetails(toDisplay, ui);

        }
    }

    private Dvd getDvdToDisplayByTitle(ConsoleIO ui, List<Dvd> allMovies) {
        Dvd toDisplay = null;

        //1. get a title from the user to be removed
        //2. look through all titles for the matching id
        boolean found = false;

        while (!found) {
            String title = ui.readString("Please enter the title to display: ");

            for (Dvd toCheck : allMovies) {
                found = toCheck.getTitle().equalsIgnoreCase(title);
                if (found) {

                    toDisplay = toCheck;
                    break;
                }
            }
        } return toDisplay;

      
    }
 
}
