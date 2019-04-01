/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author afahrenkamp
 */
public class GuessTheNumberDbDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    GuessTheNumberDbDao dao;

    public GuessTheNumberDbDaoTest() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("GuessTheNumberTest");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
        this.jdbc = new JdbcTemplate(ds);
        this.dao = new GuessTheNumberDbDao(jdbc);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        java.sql.Date firstRound = java.sql.Date.valueOf("2019-01-13 04:13:00.0");
        java.sql.Date secondRound = java.sql.Date.valueOf("1983-04-13 04:13:00.0");

        final String deleteOrganization_SupersTable = "DROP TABLE IF EXISTS Organization_Supers;";
        final String deleteSuper_SightingsTable = "DROP TABLE IF EXISTS Super_Sightings;";
        final String deleteOrganizationsTable = "DROP TABLE IF EXISTS Organizations;";
        final String deleteSupersTable = "DROP TABLE IF EXISTS Supers;";
        final String deleteSightingsTable = "DROP TABLE IF EXISTS Sightings;";
        final String deleteLocationsTable = "DROP TABLE IF EXISTS Locations;";

        final String createSupersTable = "create table Supers(superId int primary key auto_increment, `name` varchar(30) not null, description varchar(50) not null, superpower varchar(100) not null);";
        final String createRoundsTable = "create table rounds(roundId int primary key auto_increment, roundNumber int not null, guessNumber int not null, gameId int not null,"
                + "exactCorrect int, partialCorrect int, timeStamp datetime, foreign key (gameId) references games(gameId));";

        jdbc.execute(deleteOrganization_SupersTable);

        jdbc.execute(deleteSuper_SightingsTable);

        jdbc.execute(deleteOrganizationsTable);

        jdbc.execute(deleteSupersTable);

        jdbc.execute(deleteSightingsTable);

        jdbc.execute(deleteLocationsTable);

        jdbc.execute(createSupersTable);

        jdbc.execute(createRoundsTable);

        jdbc.execute(createGamesTable);

        jdbc.execute(createRoundsTable);

        jdbc.execute(createGamesTable);

        jdbc.execute(createRoundsTable);

        jdbc.update(
                "INSERT INTO games(gameId, isGameOver, targetNum) "
                + "VALUES(?,?,?)", "1", true, "1234");
        jdbc.update(
                "INSERT INTO games(gameId, isGameOver, targetNum) "
                + "VALUES(?,?,?)", "2", false, "5678");
        jdbc.update(
                "INSERT INTO rounds(roundId, roundNumber, guessNumber, gameId, exactCorrect, partialCorrect, timeStamp) "
                + "VALUES(?,?,?,?,?,?,?)", "1", "1", "7856", "2", "0", "4", firstRound);
        jdbc.update(
                "INSERT INTO rounds(roundId, roundNumber, guessNumber, gameId, exactCorrect, partialCorrect, timeStamp) "
                + "VALUES(?,?,?,?,?,?,?)", "2", "2", "5692", "2", "2", "0", secondRound);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberInMemoryDao.
     */
    @Test
    public void testGetAllGamesSucceed() throws GuessPersistenceException {
        List<Game> allGames = dao.getAllGames();
        Assert.assertEquals(2, allGames.size());
        Assert.assertNotNull(allGames);

        Game gameOne = allGames.get(0);
        Assert.assertEquals(1, gameOne.getGameId());
        Assert.assertTrue(gameOne.getIsGameOver());
        Assert.assertEquals("1234", gameOne.getTargetNum());

        Game gameTwo = allGames.get(1);
        List<Round> gameTwoRounds = allGames.get(1).getGameRounds();
        Assert.assertEquals(2, gameTwo.getGameId());
        Assert.assertFalse(gameTwo.getIsGameOver());
        Assert.assertEquals("5678", gameTwo.getTargetNum());
        Assert.assertEquals(2, gameTwoRounds.size());
    }

    /**
     * Test of getGameRounds method, of class GuessTheNumberInMemoryDao.
     */
    @Test
    public void testGetGameRoundsSucceed() throws GuessPersistenceException {
        java.sql.Timestamp firstRound = java.sql.Timestamp.valueOf("2019-01-13 04:13:00.0");
        java.sql.Timestamp secondRound = java.sql.Timestamp.valueOf("1983-04-13 04:13:00.0");

        List<Round> gameTwoRounds = dao.getGameRounds(2);
        Assert.assertEquals(2, gameTwoRounds.size());
        Assert.assertNotNull(gameTwoRounds.size());

        //note that this tests for sorting because index 0 is round 2 and index 1 is round 1 becaus eof timestamps
        Round roundOne = gameTwoRounds.get(1);
        Assert.assertEquals(1, roundOne.getRoundId());
        Assert.assertEquals("7856", roundOne.getGuessNumber());
        Assert.assertEquals(2, roundOne.getGameId());
        Assert.assertEquals(0, roundOne.getExactCorrect());
        Assert.assertEquals(4, roundOne.getPartialCorrect());
        Assert.assertEquals(firstRound, roundOne.getTimeStamp());

        Round roundTwo = gameTwoRounds.get(0);
        Assert.assertEquals(2, roundTwo.getRoundId());
        Assert.assertEquals("5692", roundTwo.getGuessNumber());
        Assert.assertEquals(2, roundTwo.getGameId());
        Assert.assertEquals(2, roundTwo.getExactCorrect());
        Assert.assertEquals(0, roundTwo.getPartialCorrect());
        Assert.assertEquals(secondRound, roundTwo.getTimeStamp());

    }

    @Test
    public void testGetGameRoundsNoRounds() throws GuessPersistenceException {
        List<Round> gameOneRounds = dao.getGameRounds(3);
        Assert.assertEquals(0, gameOneRounds.size());
    }

    /**
     * Test of updateGameStatus method, of class GuessTheNumberInMemoryDao.
     */
    @Test
    public void testUpdateGameStatusSuccess() throws GuessPersistenceException {
        List<Game> allGames = dao.getAllGames();
        Game gameTwo = allGames.get(1);
        Assert.assertFalse(gameTwo.getIsGameOver());

        dao.updateGameStatus(2, true);
        List<Game> allGamesUpdated = dao.getAllGames();
        Game gameTwoUpdated = allGamesUpdated.get(1);
        Assert.assertTrue(gameTwoUpdated.getIsGameOver());
    }

    /**
     * Test of beginGame method, of class GuessTheNumberInMemoryDao.
     */
    @Test
    public void testBeginGameSuccess() throws GuessPersistenceException {
        Game toBegin = new Game();
        toBegin.setTargetNum("3257");
        toBegin.setIsGameOver(false);
        dao.beginGame(toBegin);

        List<Game> allGames = dao.getAllGames();
        Game begunGame = allGames.get(allGames.size() - 1);
        Assert.assertEquals(3, begunGame.getGameId());
        Assert.assertFalse(begunGame.getIsGameOver());
        Assert.assertEquals("3257", begunGame.getTargetNum());
    }

    @Test
    public void testBeginGameFailEmptyGame() throws GuessPersistenceException {
        Game toBegin = new Game();
        try {
            dao.beginGame(toBegin);
            Assert.fail("Expected GuessPersistenceException was not thrown.");
        } catch (GuessPersistenceException ex) {

        }
    }

    /**
     * Test of recordGuess method, of class GuessTheNumberInMemoryDao.
     */
    @Test
    public void testRecordGuess() throws GuessPersistenceException {
        Game toBegin = new Game();
        toBegin.setTargetNum("3257");
        toBegin.setIsGameOver(false);
        dao.beginGame(toBegin);
        List<Game> allGames = dao.getAllGames();

        Round firstRound = new Round();
        firstRound.setGameId(allGames.size());
        firstRound.setGuessNumber("1304");
        firstRound.setExactCorrect(0);
        firstRound.setPartialCorrect(2);
        dao.recordGuess(firstRound, toBegin);

        List<Round> gameThreeRounds = dao.getGameRounds(3);
        toBegin.setGameRounds(gameThreeRounds);
        Round roundOneGuessed = gameThreeRounds.get(gameThreeRounds.size() - 1);
        Assert.assertEquals(1, roundOneGuessed.getRoundNumber());
        Assert.assertEquals(3, roundOneGuessed.getRoundId());
        Assert.assertEquals(2, roundOneGuessed.getPartialCorrect());
        Assert.assertEquals(0, roundOneGuessed.getExactCorrect());
        Assert.assertEquals("e:0p:2", roundOneGuessed.getGuessResult());

        List<Game> allGamesTwo = dao.getAllGames();
        Round secondRound = new Round();
        secondRound.setGameId(allGamesTwo.size());
        secondRound.setGuessNumber("3257");
        secondRound.setExactCorrect(4);
        secondRound.setPartialCorrect(0);
        dao.recordGuess(secondRound, toBegin);

        List<Round> gameThreeRoundTwo = dao.getGameRounds(3);
        toBegin.setGameRounds(gameThreeRoundTwo);
        Round roundTwoGuessed = gameThreeRoundTwo.get(gameThreeRoundTwo.size() - 1);
        Assert.assertEquals(2, roundTwoGuessed.getRoundNumber());
        Assert.assertEquals(4, roundTwoGuessed.getRoundId());
        Assert.assertEquals(0, roundTwoGuessed.getPartialCorrect());
        Assert.assertEquals(4, roundTwoGuessed.getExactCorrect());
        Assert.assertEquals("e:4p:0", roundTwoGuessed.getGuessResult());
    }

    @Test
    public void testRecordGuessFailEmptyRound() throws GuessPersistenceException {
        Game toBegin = new Game();
        toBegin.setTargetNum("3257");
        toBegin.setIsGameOver(false);
        dao.beginGame(toBegin);
        Round firstRound = new Round();
        try {
            dao.recordGuess(firstRound, toBegin);
            Assert.fail("Expected GuessPersistenceException was not thrown.");
        } catch (GuessPersistenceException ex) {

        }
    }

}
