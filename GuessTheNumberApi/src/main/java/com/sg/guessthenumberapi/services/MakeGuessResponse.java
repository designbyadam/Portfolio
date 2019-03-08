/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.models.Round;

/**
 *
 * @author afahrenkamp
 */
public class MakeGuessResponse extends Response {
    private Round  currentRound;

    /**
     * @return the currentRound
     */
    public Round getCurrentRound() {
        return currentRound;
    }

    /**
     * @param currentRound the currentRound to set
     */
    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }
}
