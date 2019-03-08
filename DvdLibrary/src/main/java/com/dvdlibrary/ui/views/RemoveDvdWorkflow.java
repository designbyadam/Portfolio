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
public class RemoveDvdWorkflow {

    DvdService service;

    public RemoveDvdWorkflow(DvdService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {
        //we're gonna let the user select by title

        ListDvdResponse allDvdResponse = service.listDvds();
        
        if (!allDvdResponse.getSuccess()) {
            ui.print("SEVERE ERROR: " + allDvdResponse.getMessage());
        } else {

            List<Dvd> allDvds = allDvdResponse.getAllMovies();
            
            int id = getIdToRemoveByTitle(ui, allDvds);

            RemoveDvdResponse response = service.removeDvd(id);

            if (response.getSuccess()) {
                //is successful
                //do we display data?

                String titleRemoved = response.getTitle();

                ui.print("Removed " + titleRemoved + "\n");
            } else {
                ui.print("Error: " + response.getMessage() + "\n");
            }
        }
    }

    private int getIdToRemoveByTitle(ConsoleIO ui, List<Dvd> allMovies) {
        int toReturn = Integer.MIN_VALUE;

        //1. get a title from the user to be removed
        //2. look through all titles for the matching id
        boolean found = false;

        while (!found) {
            String title = ui.readString("Please enter the title to remove: ");

            for (Dvd toCheck : allMovies) {
                found = toCheck.getTitle().equalsIgnoreCase(title);
                if (found) {
                    //since we found a matching title
                    //here we want to save the id of that title
                    toReturn = toCheck.getId();
                    break;
                }
            }
        }

        return toReturn;
    }

}
