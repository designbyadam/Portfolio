/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;



/**
 *
 * @author afahrenkamp
 */
public class Round {
    
    private int roundId;
    private String guessNumber;
    private int gameId;
    private String guessResult;
    private java.sql.Timestamp timeStamp;
    private int exactCorrect;
    private int partialCorrect;
    private int roundNumber;


    /**
     * @return the roundId
     */
    public int getRoundId() {
        return roundId;
    }

    /**
     * @param roundId the roundId to set
     */
    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    /**
     * @return the guessNumber
     */
    public String getGuessNumber() {
        return guessNumber;
    }

    /**
     * @param guessNumber the guessNumber to set
     */
    public void setGuessNumber(String guessNumber) {
        this.guessNumber = guessNumber;
    }

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
     * @return the guessResult
     */
    public String getGuessResult() {
        return "e:" + getExactCorrect() + "p:" + getPartialCorrect();
    }

    /**
     * @return the timeStamp
     */
    public java.sql.Timestamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(java.sql.Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the exactCorrect
     */
    public int getExactCorrect() {
        return exactCorrect;
    }

    /**
     * @param exactCorrect the exactCorrect to set
     */
    public void setExactCorrect(int exactCorrect) {
        this.exactCorrect = exactCorrect;
    }

    /**
     * @return the partialCorrect
     */
    public int getPartialCorrect() {
        return partialCorrect;
    }

    /**
     * @param partialCorrect the partialCorrect to set
     */
    public void setPartialCorrect(int partialCorrect) {
        this.partialCorrect = partialCorrect;
    }

    /**
     * @return the roundNumber
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @param roundNumber the roundNumber to set
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

}
