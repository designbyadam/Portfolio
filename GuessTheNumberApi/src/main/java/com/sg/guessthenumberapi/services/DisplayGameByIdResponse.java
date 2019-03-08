/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.models.Game;

/**
 *
 * @author afahrenkamp
 */
public class DisplayGameByIdResponse extends Response {
    private Game toDisplay;

    /**
     * @return the toDisplay
     */
    public Game getToDisplay() {
        return toDisplay;
    }

    /**
     * @param toDisplay the toDisplay to set
     */
    public void setToDisplay(Game toDisplay) {
        this.toDisplay = toDisplay;
    }
}
