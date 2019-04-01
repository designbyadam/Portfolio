/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class GuessDaoAlwaysFail implements GuessTheNumberDao {

    @Override
    public List<Game> getAllGames() throws GuessPersistenceException {
        throw new GuessPersistenceException("Error: Unable to retrieve list of games.");
    }

    @Override
    public List<Round> getGameRounds(int gameId) throws GuessPersistenceException {
        throw new GuessPersistenceException("Error: Unable to retrieve list of guesses.");
    }

    @Override
    public boolean updateGameStatus(int gameId, boolean isDone) throws GuessPersistenceException {
        throw new GuessPersistenceException("Error: Unable to update status.");
    }

    @Override
    public int beginGame(Game toBegin) throws GuessPersistenceException {
        throw new GuessPersistenceException("Error: Unable to begin game.");
    }

    @Override
    public Round recordGuess(Round currentRound, Game currentGame) throws GuessPersistenceException {
        throw new GuessPersistenceException("Error: Unable to save guess.");
    }

}
