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
public class ListDvdWorkflow {

    DvdService service;

    public ListDvdWorkflow(DvdService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {
        ListDvdResponse listResponse = service.listDvds();

        if (!listResponse.getSuccess()) {
            ui.print("SEVERE ERROR: " + listResponse.getMessage());
        } else {
            List<Dvd> allDvds = listResponse.getAllMovies();
            for (Dvd currentDvd : allDvds) {
                ui.print("\n" + currentDvd.getId() + ": "
                        + currentDvd.getTitle() + "\n");
            }
        }
    }

}
