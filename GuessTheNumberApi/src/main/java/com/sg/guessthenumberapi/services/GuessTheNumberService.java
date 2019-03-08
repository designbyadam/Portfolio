/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.data.GuessPersistenceException;
import com.sg.guessthenumberapi.data.GuessTheNumberDao;
import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author afahrenkamp
 */
@Service
public class GuessTheNumberService {

    @Autowired
    GuessTheNumberDao dao;

    public GuessTheNumberService(GuessTheNumberDao dao) {
        this.dao = dao;
    }

    public GuessTheNumberService() {

    }

    public BeginGameResponse beginGame() {
        Game toBegin = new Game();
        BeginGameResponse response = new BeginGameResponse();
        try {
            String winningNumber = generateNumber();
            validateNumber(winningNumber);
            toBegin.setTargetNum(winningNumber);
            toBegin.setIsGameOver(false);
            int gameID = dao.beginGame(toBegin);
            toBegin.setGameId(gameID);
            response.setBegunGame(toBegin);
            response.setSuccess(true);
        } catch (InvalidNumberException | GuessPersistenceException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public MakeGuessResponse makeGuess(String guess, int gameId) {
        Round currentRound = new Round();
        Game currentGame = new Game();

        MakeGuessResponse response = new MakeGuessResponse();
        try {
            List<Game> allGames = dao.getAllGames();
            currentGame = getGame(gameId, allGames);
            checkCurrentGameStatus(currentGame);
            currentRound.setGameId(gameId);
            validateNumber(guess);
            currentRound.setGuessNumber(guess);
            Round roundToCheckGuess = checkGuess(guess, currentGame.getTargetNum(), currentRound);
            currentRound.setExactCorrect(roundToCheckGuess.getExactCorrect());
            currentRound.setPartialCorrect(roundToCheckGuess.getPartialCorrect());
            currentRound = dao.recordGuess(currentRound, currentGame);
            if (currentRound.getExactCorrect() == 4) {
                currentGame.setIsGameOver(true);
            }
            dao.updateGameStatus(gameId, currentGame.getIsGameOver());
            response.setCurrentRound(currentRound);
            response.setSuccess(true);
        } catch (InvalidNumberException | GuessPersistenceException
                | InvalidGameIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public ListGamesResponse getAllGames() {
        ListGamesResponse response = new ListGamesResponse();
        List<Game> toDisplay = new ArrayList();
        try {
            List<Game> allGames = dao.getAllGames();
            toDisplay = getFilteredAnswerList(allGames);
            response.setSuccess(true);
            response.setAllGames(toDisplay);
        } catch (GuessPersistenceException | NoGamesToDisplayException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public DisplayGameByIdResponse getGameById(int gameId) {
        DisplayGameByIdResponse response = new DisplayGameByIdResponse();
        Game toDisplay = new Game();
        List<Game> filteredAnswers = new ArrayList();
        try {
            List<Game> allGames = dao.getAllGames();
            filteredAnswers = getFilteredAnswerList(allGames);
            toDisplay = getGame(gameId, filteredAnswers);
            response.setToDisplay(toDisplay);
            response.setSuccess(true);
        } catch (GuessPersistenceException
                | NoGamesToDisplayException | InvalidGameIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public ListRoundsResponse getGameRounds(int gameId) {
        ListRoundsResponse response = new ListRoundsResponse();
        List<Round> allRounds = new ArrayList();
        List<Game> allGames = new ArrayList();
        try {
            allGames = dao.getAllGames();
            getGame(gameId, allGames);
            allRounds = dao.getGameRounds(gameId);
            validate(allRounds);
            response.setSuccess(true);
            response.setAllRounds(allRounds);
        } catch (GuessPersistenceException | NoRoundsToDisplayException
                | InvalidGameIdException ex) {
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    private String generateNumber() {
        Random rng = new Random();
        String toReturn = "";
        List<Integer> numbers = new ArrayList();
        while (numbers.size() < 4) {
            int randomNum = rng.nextInt(10);
            if (numbers.size() < 1 && randomNum == 0) {
            } else if (!numbers.contains(randomNum)) {

                numbers.add(randomNum);
                toReturn += randomNum;
            }
        }
        return toReturn;
    }

    private boolean validateNumber(String fourDigitNumber) throws InvalidNumberException {
        boolean isValid = true;
        List<Character> winningArray = new ArrayList<>();
        if (fourDigitNumber.length() != 4) {
            throw new InvalidNumberException("Number must be four digits.");
        }
        for (Character toCheck : fourDigitNumber.toCharArray()) {
            int intCheck = Character.getNumericValue(toCheck);
            if (intCheck < 0) {
                throw new InvalidNumberException("Guess must be a number.");
            } else if (winningArray.contains(toCheck)) {
                isValid = false;
                throw new InvalidNumberException("Number cannot have any repeated digits.");
            } else {
                winningArray.add(toCheck);
            }
        }
        return isValid;
    }

    private Round checkGuess(String currentGuess, String winningNumber, Round toGuess) {
        Round toReturn = toGuess;
        int exactCorrect = 0;
        int partialCorrect = 0;
        List<Character> guessArray = new ArrayList<>();
        List<Character> winningArray = new ArrayList<>();
        for (Character toCheck : currentGuess.toCharArray()) {
            guessArray.add(toCheck);
        }
        for (Character toCheck : winningNumber.toCharArray()) {
            winningArray.add(toCheck);
        }
        for (int i = 0; i < winningArray.size(); i++) {
            if (winningArray.get(i) == guessArray.get(i)) {
                exactCorrect++;
            } else if (winningArray.contains(guessArray.get(i))) {
                partialCorrect++;
            }
        }
        toReturn.setExactCorrect(exactCorrect);
        toReturn.setPartialCorrect(partialCorrect);
        return toReturn;
    }

    private Game getGame(int gameId, List<Game> allGames) throws InvalidGameIdException {
        Game toReturn = new Game();

        for (Game toCheck : allGames) {
            if (gameId == toCheck.getGameId()) {
                toReturn = toCheck;
                break;
            } else {
                toReturn = null;
            }
        }
        if (toReturn == null) {
            throw new InvalidGameIdException("Invalid Game ID was entered.");
        }

        return toReturn;
    }

    private void checkCurrentGameStatus(Game currentGame) throws InvalidGameIdException {
        if (currentGame.getIsGameOver()) {
            throw new InvalidGameIdException("Game you are guessing for is already complete.");
        }
    }

    private List<Game> getFilteredAnswerList(List<Game> allGames) throws NoGamesToDisplayException {
        List<Game> toReturn = new ArrayList();
        if (allGames.isEmpty()) {
            throw new NoGamesToDisplayException("No games to display.");
        } else {
            for (Game toCheck : allGames) {
                if (!toCheck.getIsGameOver()) {
                    toCheck.setTargetNum(null);
                }
                toReturn.add(toCheck);
            }
        }
        return toReturn;
    }

    private void validate(List<Round> allRounds) throws NoRoundsToDisplayException {
        if (allRounds.isEmpty()) {
            throw new NoRoundsToDisplayException("No rounds to display for that game.");
        }
    }
}
