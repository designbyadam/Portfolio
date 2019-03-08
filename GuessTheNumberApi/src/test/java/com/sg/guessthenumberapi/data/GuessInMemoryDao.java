/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class GuessInMemoryDao implements GuessTheNumberDao {

    List<Game> allGames = new ArrayList();
    List<Round> gameOneRounds = new ArrayList();

    public GuessInMemoryDao() {

        Game gameOne = new Game();
        gameOne.setGameId(1);
        gameOne.setTargetNum("1234");
        gameOne.setIsGameOver(false);

        Game gameTwo = new Game();
        gameTwo.setGameId(2);
        gameTwo.setTargetNum("5679");
        gameTwo.setIsGameOver(true);

        Round roundOne = new Round();
        roundOne.setGameId(1);
        roundOne.setRoundId(1);
        roundOne.setGuessNumber("5678");
        roundOne.setExactCorrect(0);
        roundOne.setPartialCorrect(0);

        Round roundTwo = new Round();
        roundTwo.setGameId(1);
        roundTwo.setRoundId(2);
        roundTwo.setGuessNumber("1039");
        roundTwo.setExactCorrect(2);
        roundTwo.setPartialCorrect(0);

        Round roundThree = new Round();
        roundThree.setGameId(1);
        roundThree.setRoundId(3);
        roundThree.setGuessNumber("2108");
        roundThree.setExactCorrect(0);
        roundThree.setPartialCorrect(2);

        gameOneRounds.add(roundOne);
        gameOneRounds.add(roundTwo);
        gameOneRounds.add(roundThree);

        gameOne.setGameRounds(gameOneRounds);

        allGames.add(gameOne);
        allGames.add(gameTwo);

    }

    @Override
    public List<Game> getAllGames() {
        return allGames;
    }

    @Override
    public List<Round> getGameRounds(int gameId) {
        return gameOneRounds;
    }

    @Override
    public boolean updateGameStatus(int gameId, boolean isDone) {
        return isDone;
    }

    @Override
    public int beginGame(Game toBegin) {
        int gameId = generateGameId(allGames);
        toBegin.setGameId(gameId);
        allGames.add(toBegin);
        return toBegin.getGameId();
    }

    @Override
    public Round recordGuess(Round currentRound, Game currentGame) {
        
        int roundId = generateRoundId(gameOneRounds);
        int roundNumber = calculateNumberOfRounds(currentGame);
        currentRound.setRoundNumber(roundNumber);
        currentRound.setRoundId(roundId);
        gameOneRounds.add(currentRound);
        return currentRound;
    }

    private int generateGameId(List<Game> allGames) {
        int toReturn = Integer.MIN_VALUE;

        if (allGames.isEmpty()) {
            toReturn = 1;
        } else {
            for (Game toInspect : allGames) {
                if (toInspect.getGameId() > toReturn) {
                    toReturn = toInspect.getGameId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

    private int generateRoundId(List<Round> gameOneRounds) {
        int toReturn = Integer.MIN_VALUE;

        if (gameOneRounds.isEmpty()) {
            toReturn = 1;
        } else {
            for (Round toInspect : gameOneRounds) {
                if (toInspect.getRoundId() > toReturn) {
                    toReturn = toInspect.getRoundId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

    private int calculateNumberOfRounds(Game currentGame) {
        List<Round> gameRounds = currentGame.getGameRounds();
        int toReturn = Integer.MIN_VALUE;

        if (gameRounds == null) {
            toReturn = 1;
        } else if (gameRounds.isEmpty()) {
            toReturn = 1;
        } else {
            for (Round toInspect : gameRounds) {
                if (toInspect.getRoundNumber() > toReturn) {
                    toReturn = toInspect.getRoundNumber();
                }
            }

            toReturn++;
        }

        return toReturn;
    }
}
