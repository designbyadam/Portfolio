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
public class BeginGameResponse extends Response {
    private Game begunGame;

    /**
     * @return the begunGame
     */
    public Game getBegunGame() {
        return begunGame;
    }

    /**
     * @param begunGame the begunGame to set
     */
    public void setBegunGame(Game begunGame) {
        this.begunGame = begunGame;
    }
}
