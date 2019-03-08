/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.services;

import com.dvdlibrary.daos.DvdDao;
import com.dvdlibrary.dtos.Dvd;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class DvdService {

    DvdDao dao;

    public DvdService(DvdDao dao) {
        this.dao = dao;
    }

    public AddDvdResponse addDvd(Dvd toAdd) {
        AddDvdResponse toReturn = new AddDvdResponse();

        //1. validate the Dvd
        boolean success = true;
        String message = validate(toAdd);

        success = message.length() < 1;

        if (success) {
            //2. if valid, send to DAO
            //3.    if add successful, send back success response w/ Dvd
            //4.    else, send back error message from the DAO  

            Dvd created = dao.addDvd(toAdd);
            if (created == null) {
                toReturn.setSuccess(false);
                toReturn.setMessage("Failed to add Dvd.");
            } else {
                toReturn.setMovie(created);
                toReturn.setSuccess(true);
            }

        } else {
            //5. else, send error message back with validation failure
            toReturn.setMessage(message);
            toReturn.setSuccess(success);
        }

        return toReturn;
    }

    public RemoveDvdResponse removeDvd(int id) {
        RemoveDvdResponse toReturn = new RemoveDvdResponse();

        //validate the incoming id value
        //  get all the existing movies
        //TODO: add validation to make sure we grab the movies
        //successfully
        List<Dvd> allMovies = dao.listDvds();

        //  determine if we can see the sent id
        boolean isValidId = validateId(id, allMovies);

        if (isValidId) {

            //todo: double check we actually got a dvd object
            Dvd matching = dao.getDvdById(id);

            //if valid, send the request off to the dao
            boolean success = dao.removeDvd(id);
            //      check if the dao was successful
            if (success) {
                //if successful, return success response
                toReturn.setSuccess(true);
                toReturn.setTitle(matching.getTitle());
            } else {
                // else, indicate dao failure
                toReturn.setSuccess(false);
                toReturn.setMessage("Failed to remove movie.");
            }
        } else {
            //id was not valid...
            toReturn.setSuccess(false);
            //need to tell the user
            toReturn.setMessage("Invalid ID.");
        }

        return toReturn;
    }

    public ListDvdResponse listDvds() {
        ListDvdResponse toReturn = new ListDvdResponse();

        List<Dvd> allDvds = dao.listDvds();

        //we expect our dao to give an EMPTY (but non-null)
        //list if it succeeds but there are no movies
        if (allDvds == null) {
            //there was an error
            toReturn.setSuccess(false);
            toReturn.setMessage("Failed to retrieve dvds.");
        } else {
            toReturn.setSuccess(true);
            toReturn.setAllMovies(allDvds);
        }

        return toReturn;
    }

    public EditDvdResponse editDvd(Dvd toEdit) {
        EditDvdResponse response = new EditDvdResponse();

        //validate Dvd
        boolean success = true;
        String message = validate(toEdit);

        success = message.length() < 1;

        if (success) {

            Dvd edited = dao.editDvd(toEdit);
            if (edited == null) {
                response.setMessage("Error: could not edit movie.");
                response.setSuccess(false);
            } else {
                response.setEditedMovie(edited);
                response.setSuccess(true);
            }

        } else {
            response.setSuccess(false);
            response.setMessage(message);
        }

        return response;
    }

    private boolean validateId(int id, List<Dvd> allMovies) {

        boolean isValid = false;

        for (Dvd toCheck : allMovies) {
            if (id == toCheck.getId()) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }

    private String validate(Dvd toAdd) {
        boolean success = true;
        String message = "";

        if (toAdd.getTitle().equals("")) {
            success = false;
            message = "Title must be at least one character long.";
        }

        if (success && toAdd.getDirector().equals("")) {
            success = false;
            message = "Director must be at least one character long.";
        }

        if (success && toAdd.getStudio().equals("")) {
            success = false;
            message = "Director must be at least one character long.";
        }

        if (success
                && !(toAdd.getRating().equalsIgnoreCase("G")
                || toAdd.getRating().equalsIgnoreCase("PG")
                || toAdd.getRating().equalsIgnoreCase("PG-13")
                || toAdd.getRating().equalsIgnoreCase("R")
                || toAdd.getRating().equalsIgnoreCase("unrated"))) {
            success = false;
            message = "Rating must be G/PG/PG-13/R/or unrated";
        }

        //TODO: un-hard-code the current year
        if (success
                && (toAdd.getReleaseYear() < 1900
                || toAdd.getReleaseYear() > 2019)) {
            success = false;
            message = "Year must be between 1900 and 2019";
        }

        return message;
    }

}
