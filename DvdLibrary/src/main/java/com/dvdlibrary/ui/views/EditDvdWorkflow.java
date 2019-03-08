/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.views;

import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.services.DvdService;
import com.dvdlibrary.services.EditDvdResponse;
import com.dvdlibrary.services.ListDvdResponse;
import com.dvdlibrary.ui.ConsoleIO;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class EditDvdWorkflow extends Workflow {

    DvdService service;

    public EditDvdWorkflow(DvdService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {

        ListDvdResponse listResponse = service.listDvds();

        if (listResponse.getSuccess()) {
            //we got all the movies successfully
            //get some input from the user
            //      what movie to edit?
            Dvd toEdit = getDvdToEditByTitle(ui, listResponse.getAllMovies());

            //      cyle through each property 
            //      if the user enters a blank, leave the existing data
            //      modify the properties of our Dvd based on the user input
            String newTitle = ui.readString("Please enter new title (blank to keep: " + toEdit.getTitle() + "): ");
            if (!newTitle.isEmpty()) {
                toEdit.setTitle(newTitle);
            }

            //either going to get new value
            //or return the old value
            int newYear = getYear(ui, toEdit.getReleaseYear());
            toEdit.setReleaseYear(newYear);

            String newRating = getRating(ui, toEdit.getRating());
            toEdit.setRating(newRating);

            String newDirector = ui.readString("Please enter new director (blank to keep: " + toEdit.getDirector() + "): ");
            if (!newDirector.isEmpty()) {
                toEdit.setDirector(newDirector);
            }

            String newStudio = ui.readString("Please enter new studio (blank to keep: " + toEdit.getStudio() + "): ");
            if (!newStudio.isEmpty()) {
                toEdit.setStudio(newStudio);
            }

            String newNote = ui.readString("Please enter new note (blank to keep: " + toEdit.getNote() + "): ");
            if (!newNote.isEmpty()) {
                toEdit.setNote(newNote);
            }

            // send to service layer
            EditDvdResponse response = service.editDvd(toEdit);

            // read the response
            if (response.getSuccess()) {
                //if successful, show the edited movie
                Dvd editedMovie = response.getEditedMovie();
                displayDvdDetails(editedMovie, ui);
            } else {
                //else, display error message
                ui.print("Error: " + response.getMessage());
            }

        } else {
            //couldn't even list movies
            //show serious error to user
            ui.print("SEVERE ERROR: " + listResponse.getMessage());
        }
    }

    private Dvd getDvdToEditByTitle(ConsoleIO ui, List<Dvd> allMovies) {
        Dvd toReturn = null;

        if (allMovies.size() > 0) {
            boolean found = false;
            while (!found) {
                String title = ui.readString("Please enter the title to edit: ");
                for (Dvd toCheck : allMovies) {
                    if (title.equalsIgnoreCase(toCheck.getTitle())) {
                        toReturn = toCheck;
                        found = true;
                        break;
                    }

                }
            }
        }

       return toReturn;
    }

    private int getYear(ConsoleIO ui, int releaseYear) {
        int toReturn = releaseYear;
        
        boolean validYear = false;
        while( !validYear ){
            String newYear = ui.readString("Please enter new year (blank to keep: " + releaseYear + "): ");
            if (newYear.isEmpty()) {
                toReturn = releaseYear;
                validYear = true;
            } else {
                //user typed SOMETHING in
                //may or may not be valid
                
                try
                {
                    toReturn = Integer.parseInt(newYear);
                    if( toReturn >=  1900 && toReturn <= 2019 ){
                        validYear = true;
                    }
                }
                catch( NumberFormatException ex ){
                    //here we just eat the exception
                    //user input failure is very normal
                }
            }
            
            
        }
        
        return toReturn;
    }

    private String getRating(ConsoleIO ui, String oldRating) {
        String toReturn = null;
        
        boolean validRating = false;
        while( !validRating ){
            String newRating = ui.readString("Please enter new rating (blank to keep: " + oldRating + "): ");
            if (newRating.isEmpty()) {
                toReturn = oldRating;
                validRating = true;
            }
            else
            {
                //if not empty, could be valid, could be invalid
                toReturn = newRating;
                validRating = 
                        newRating.equalsIgnoreCase("G") ||
                        newRating.equalsIgnoreCase("PG") ||
                        newRating.equalsIgnoreCase("PG-13") ||
                        newRating.equalsIgnoreCase("R") ||
                        newRating.equalsIgnoreCase("unrated");
            }
        }
        
        return toReturn;
    }

}
