/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.views;

import com.dvdlibrary.services.AddDvdResponse;
import com.dvdlibrary.services.DvdService;
import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.ui.ConsoleIO;

/**
 *
 * @author afahrenkamp
 */
public class AddDvdWorkflow extends Workflow {

    DvdService service;

    public AddDvdWorkflow(DvdService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {
        Dvd toBuild = new Dvd();

        String title = getTitle(ui);
        int year = getYear(ui);
        String director = getDirector(ui);
        String rating = getRating(ui);
        String studio = getStudio(ui);
        String note = getNote(ui);

        toBuild.setTitle(title);
        toBuild.setReleaseYear(year);
        toBuild.setDirector(director);
        toBuild.setRating(rating);
        toBuild.setStudio(studio);
        toBuild.setNote(note);

        AddDvdResponse response = service.addDvd(toBuild);

        if (response.getSuccess()) {
            Dvd returnedMovie = response.getMovie();
            displayDvdDetails(returnedMovie, ui);
        } else {
            ui.print("Error: " + response.getMessage());
        }
    }

    private String getTitle(ConsoleIO ui) {
        String title;

        do {
            title = ui.readString("Please enter title: ");
        } while (title.equals(""));   //user is not allowed to
        //enter a blank string
        return title;
    }

    private int getYear(ConsoleIO ui) {
        int year = Integer.MIN_VALUE;

        //TODO: un-hard-code the current year
        year = ui.readInt("Please enter year: ", 1900, 2019);

        return year;
    }

    private String getDirector(ConsoleIO ui) {
        String director;

        do {
            director = ui.readString("Please enter director: ");
        } while (director.equals(""));   //user is not allowed to
        //enter a blank string
        return director;
    }

    private String getRating(ConsoleIO ui) {
        //must be G, PG, PG-13, R, or unrated

        String rating = null;

        boolean isValid = false;
        while (!isValid) {
            rating = ui.readString("Please enter rating (G/PG/PG-13/R/unrated): ");

            isValid
                    = rating.equalsIgnoreCase("G")
                    || rating.equalsIgnoreCase("PG")
                    || rating.equalsIgnoreCase("PG-13")
                    || rating.equalsIgnoreCase("R")
                    || rating.equalsIgnoreCase("unrated");

        }

        return rating;
    }

    private String getStudio(ConsoleIO ui) {
        String studio;

        do {
            studio = ui.readString("Please enter studio: ");
        } while (studio.equals(""));      //user is not allowed to
        //enter a blank string
        return studio;
    }

    private String getNote(ConsoleIO ui) {
        String note = ui.readString("Please enter note (optional): ");
        return note;
    }

}
