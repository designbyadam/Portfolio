/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.models;

import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class Game {
    private int gameId;
    private boolean isGameOver;
    private String targetNum;
    private List<Round> gameRounds;

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the isGameOver
     */
    public boolean getIsGameOver() {
        return isGameOver;
    }

    /**
     * @param isGameOver the isGameOver to set
     */
    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    /**
     * @return the targetNum
     */
    public String getTargetNum() {
        return targetNum;
    }

    /**
     * @param targetNum the targetNum to set
     */
    public void setTargetNum(String targetNum) {
        this.targetNum = targetNum;
    }

    /**
     * @return the gameRounds
     */
    public List<Round> getGameRounds() {
        return gameRounds;
    }

    /**
     * @param gameRounds the gameRounds to set
     */
    public void setGameRounds(List<Round> gameRounds) {
        this.gameRounds = gameRounds;
    }
}
