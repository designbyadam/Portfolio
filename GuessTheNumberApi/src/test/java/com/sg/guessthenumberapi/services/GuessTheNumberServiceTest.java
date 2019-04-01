/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.services;

import com.sg.guessthenumberapi.data.GuessDaoAlwaysFail;
import com.sg.guessthenumberapi.data.GuessInMemoryDao;
import com.sg.guessthenumberapi.data.GuessPersistenceException;
import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import junit.framework.Assert;
import static junit.framework.Assert.fail;
import static junit.framework.Assert.fail;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author afahrenkamp
 */
public class GuessTheNumberServiceTest {

    public GuessTheNumberServiceTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of beginGame method, of class GuessTheNumberService.
     */
    @Test
    public void testBeginGameSuccess() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);
        BeginGameResponse responseOne = service.beginGame();
        List<Character> winningArray = new ArrayList<>();
        Game toBeginOne = new Game();

        toBeginOne.setTargetNum(responseOne.getBegunGame().getTargetNum());
        toBeginOne.setGameId(responseOne.getBegunGame().getGameId());
        toBeginOne.setIsGameOver(responseOne.getBegunGame().getIsGameOver());
        IntStream.range(0, toBeginOne.getTargetNum().length()).forEach(index -> winningArray.add(toBeginOne.getTargetNum().charAt(index)));
        char firstCell = winningArray.get(0);
        char secondCell = winningArray.get(1);
        char thirdCell = winningArray.get(2);
        char fourthCell = winningArray.get(3);
        int firstCellInt = Character.getNumericValue(firstCell);
        int secondCellInt = Character.getNumericValue(secondCell);
        int thirdCellInt = Character.getNumericValue(thirdCell);
        int fourthCellInt = Character.getNumericValue(fourthCell);
        boolean noRepeat = (firstCellInt != secondCellInt
                && secondCellInt != thirdCellInt && thirdCellInt != fourthCellInt);

        //check winning numbers for size and make sure the overall string is
        //composed of numbers from 0-9 with no repeats
        Assert.assertEquals(4, winningArray.size());
        Assert.assertTrue(firstCellInt >= 0 && firstCellInt <= 9);
        Assert.assertTrue(secondCellInt >= 0 && secondCellInt <= 9);
        Assert.assertTrue(thirdCellInt >= 0 && thirdCellInt <= 9);
        Assert.assertTrue(fourthCellInt >= 0 && fourthCellInt <= 9);
        Assert.assertFalse(toBeginOne.getIsGameOver());
        Assert.assertEquals(3, toBeginOne.getGameId());
        Assert.assertTrue(noRepeat);

        //add second game to make sure gameId increases and all other asserts 
        //are true for this game as well
        List<Character> winningArrayTwo = new ArrayList<>();
        Game toBeginTwo = new Game();
        BeginGameResponse responseTwo = service.beginGame();

        toBeginTwo.setTargetNum(responseTwo.getBegunGame().getTargetNum());
        toBeginTwo.setGameId(responseTwo.getBegunGame().getGameId());
        toBeginTwo.setIsGameOver(responseTwo.getBegunGame().getIsGameOver());
        IntStream.range(0, toBeginTwo.getTargetNum().length()).forEach(index -> winningArrayTwo.add(toBeginTwo.getTargetNum().charAt(index)));
        char firstCellNew = winningArrayTwo.get(0);
        char secondCellNew = winningArrayTwo.get(1);
        char thirdCellNew = winningArrayTwo.get(2);
        char fourthCellNew = winningArrayTwo.get(3);
        int firstCellIntNew = Character.getNumericValue(firstCellNew);
        int secondCellIntNew = Character.getNumericValue(secondCellNew);
        int thirdCellIntNew = Character.getNumericValue(thirdCellNew);
        int fourthCellIntNew = Character.getNumericValue(fourthCellNew);
        boolean noRepeatNew = (firstCellIntNew != secondCellIntNew
                && secondCellIntNew != thirdCellIntNew && thirdCellIntNew != fourthCellIntNew);

        Assert.assertEquals(4, winningArrayTwo.size());
        Assert.assertTrue(firstCellIntNew >= 0 && firstCellIntNew <= 9);
        Assert.assertTrue(secondCellIntNew >= 0 && secondCellIntNew <= 9);
        Assert.assertTrue(thirdCellIntNew >= 0 && thirdCellIntNew <= 9);
        Assert.assertTrue(fourthCellIntNew >= 0 && fourthCellIntNew <= 9);
        Assert.assertFalse(toBeginTwo.getIsGameOver());
        Assert.assertEquals(4, toBeginTwo.getGameId());
        Assert.assertTrue(noRepeatNew);
    }

    @Test
    public void testBeginGameFail() {
        GuessDaoAlwaysFail failDao = new GuessDaoAlwaysFail();
        GuessTheNumberService failService = new GuessTheNumberService(failDao);

        BeginGameResponse failResponse = failService.beginGame();
        Assert.assertNull(failResponse.getBegunGame());
        Assert.assertFalse(failResponse.isSuccess());
    }

    /**
     * Test of makeGuess method, of class GuessTheNumberService.
     */
    @Test
    public void testMakeGuessSuccess() {
        String guessOne = "5678";
        String guessTwo = "2140";
        String guessThree = "3214";
        String guessFour = "1234";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guessOne, 1);
        Game gameOne = dao.getAllGames().get(0);
        Assert.assertTrue(responseOne.isSuccess());
        Assert.assertEquals("e:0p:0", responseOne.getCurrentRound().getGuessResult());
        Assert.assertEquals(4, responseOne.getCurrentRound().getRoundId());
        Assert.assertFalse(gameOne.getIsGameOver());

        MakeGuessResponse responseTwo = service.makeGuess(guessTwo, 1);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertEquals("e:0p:3", responseTwo.getCurrentRound().getGuessResult());
        Assert.assertEquals(5, responseTwo.getCurrentRound().getRoundId());
        Game gameOnePartTwo = dao.getAllGames().get(0);
        Assert.assertFalse(gameOnePartTwo.getIsGameOver());

        MakeGuessResponse responseThree = service.makeGuess(guessThree, 1);
        Assert.assertTrue(responseThree.isSuccess());
        Assert.assertEquals("e:2p:2", responseThree.getCurrentRound().getGuessResult());
        Assert.assertEquals(6, responseThree.getCurrentRound().getRoundId());
        Game gameOnePartThree = dao.getAllGames().get(0);
        Assert.assertFalse(gameOnePartThree.getIsGameOver());

        MakeGuessResponse responseFour = service.makeGuess(guessFour, 1);
        Assert.assertTrue(responseFour.isSuccess());
        Assert.assertEquals("e:4p:0", responseFour.getCurrentRound().getGuessResult());
        Assert.assertEquals(7, responseFour.getCurrentRound().getRoundId());
        Game gameOnePartFour = dao.getAllGames().get(0);
        Assert.assertTrue(gameOnePartFour.getIsGameOver());
    }

    public void testMakeGuessFailFailDao() {
        String guess = "6789";
        GuessDaoAlwaysFail dao = new GuessDaoAlwaysFail();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailTooManyNumbers() {
        String guess = "56789";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailTooFewNumbers() {
        String guess = "567";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailRepeatedDigit() {
        String guess = "6787";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailNonNumeric() {
        String guess = "abdt";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailNull() {
        String guess = null;
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 1);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailAlreadyCompletedGame() {
        String guess = "6789";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 2);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    public void testMakeGuessFailInvalidGameId() {
        String guess = "6789";
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        MakeGuessResponse responseOne = service.makeGuess(guess, 3);
        Assert.assertFalse(responseOne.isSuccess());
        Assert.assertNull(responseOne.getCurrentRound().getRoundId());
        Assert.assertNull(responseOne.getCurrentRound().getGuessResult());
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberService.
     */
    @Test
    public void testGetAllGamesSuccess() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        ListGamesResponse response = service.getAllGames();
        List<Game> gamesToCheck = service.getAllGames().getAllGames();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllGames());
        Assert.assertEquals(2, gamesToCheck.size());
        Assert.assertEquals("Hidden", gamesToCheck.get(0).getTargetNum());
        Assert.assertEquals("5679", gamesToCheck.get(1).getTargetNum());
        Assert.assertEquals(3, gamesToCheck.get(0).getGameRounds().size());
    }

    @Test
    public void testGetAllGamesFail() {
        GuessDaoAlwaysFail failDao = new GuessDaoAlwaysFail();
        GuessTheNumberService failService = new GuessTheNumberService(failDao);

        ListGamesResponse failResponse = failService.getAllGames();
        Assert.assertNull(failResponse.getAllGames());
        Assert.assertFalse(failResponse.isSuccess());
    }

    /**
     * Test of getGameById method, of class GuessTheNumberService.
     */
    @Test
    public void testGetGameByIdSuccess() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        DisplayGameByIdResponse response = service.getGameById(1);
        Game gameOne = service.getGameById(1).getToDisplay();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(gameOne);
        Assert.assertEquals("Hidden", gameOne.getTargetNum());
        Assert.assertFalse(gameOne.getIsGameOver());
        Assert.assertEquals(3, gameOne.getGameRounds().size());

        DisplayGameByIdResponse responseTwo = service.getGameById(2);
        Game gameTwo = service.getGameById(2).getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(gameTwo);
        Assert.assertEquals("5679", gameTwo.getTargetNum());
        Assert.assertTrue(gameTwo.getIsGameOver());
        Assert.assertNull(gameTwo.getGameRounds());
    }

    @Test
    public void testGetGameByIdFail() {
        GuessDaoAlwaysFail failDao = new GuessDaoAlwaysFail();
        GuessTheNumberService failService = new GuessTheNumberService(failDao);

        DisplayGameByIdResponse failResponse = failService.getGameById(1);
        Assert.assertNull(failResponse.getToDisplay());
        Assert.assertFalse(failResponse.isSuccess());

    }

    @Test
    public void testGetGameByIdFailInvalidNoGameId() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        DisplayGameByIdResponse response = service.getGameById(5);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToDisplay());

    }

    /**
     * Test of getGameRounds method, of class GuessTheNumberService.
     */
    @Test
    public void testGetGameRoundsSuccess() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        ListRoundsResponse response = service.getGameRounds(1);
        List<Round> roundsGameOne = service.getGameRounds(1).getAllRounds();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(roundsGameOne);
        Assert.assertEquals(3, roundsGameOne.size());
        Assert.assertEquals("5678", roundsGameOne.get(0).getGuessNumber());
        Assert.assertEquals("1039", roundsGameOne.get(1).getGuessNumber());
        Assert.assertEquals("2108", roundsGameOne.get(2).getGuessNumber());
        Assert.assertEquals("e:0p:0", roundsGameOne.get(0).getGuessResult());
        Assert.assertEquals("e:2p:0", roundsGameOne.get(1).getGuessResult());
        Assert.assertEquals("e:0p:2", roundsGameOne.get(2).getGuessResult());
    }

    @Test
    public void testGetGameRoundsFail() {
        GuessDaoAlwaysFail failDao = new GuessDaoAlwaysFail();
        GuessTheNumberService failService = new GuessTheNumberService(failDao);

        ListRoundsResponse failResponse = failService.getGameRounds(1);
        Assert.assertFalse(failResponse.isSuccess());

    }

    @Test
    public void testGetGameRoundsInvalidNoGameId() {
        GuessInMemoryDao dao = new GuessInMemoryDao();
        GuessTheNumberService service = new GuessTheNumberService(dao);

        ListRoundsResponse response = service.getGameRounds(5);
        Assert.assertFalse(response.isSuccess());

    }
}
