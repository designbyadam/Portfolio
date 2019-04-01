/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.models.Round;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListRoundsResponse extends Response {
    private List<Round> allRounds = new ArrayList();

    /**
     * @return the allRounds
     */
    public List<Round> getAllRounds() {
        return allRounds;
    }

    /**
     * @param allRounds the allRounds to set
     */
    public void setAllRounds(List<Round> allRounds) {
        this.allRounds = allRounds;
    }
}
