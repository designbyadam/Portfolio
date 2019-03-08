/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.models.Game;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListGamesResponse extends Response {
    private List<Game> allGames;

    /**
     * @return the allGames
     */
    public List<Game> getAllGames() {
        return allGames;
    }

    /**
     * @param allGames the allGames to set
     */
    public void setAllGames(List<Game> allGames) {
        this.allGames = allGames;
    }
}
