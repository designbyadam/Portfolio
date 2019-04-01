/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface GuessTheNumberDao {

    List<Game> getAllGames() throws GuessPersistenceException;

//    Game getSingleGame(int gameId) throws GuessPersistenceException;

    List<Round> getGameRounds(int gameId) throws GuessPersistenceException;

    boolean updateGameStatus(int gameId, boolean isDone) throws GuessPersistenceException;

    int beginGame(Game toBegin) throws GuessPersistenceException;

    Round recordGuess(Round currentRound, Game currentGame) throws GuessPersistenceException;

}
