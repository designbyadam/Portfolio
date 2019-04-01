/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.data;

import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("database")
public class GuessTheNumberDbDao implements GuessTheNumberDao {

    @Autowired
    private JdbcTemplate jdbc;
    
    public GuessTheNumberDbDao() {
        
    }
    
    public GuessTheNumberDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Game> getAllGames() throws GuessPersistenceException {
        List<Game> allGames = new ArrayList();
        try {
            allGames = jdbc.query("SELECT * FROM games", new gameMapper());
            for (Game toBuild : allGames) {
                toBuild.setGameRounds(jdbc.query("SELECT * FROM rounds WHERE gameId = ?", new roundMapper(),
                        toBuild.getGameId()));
            }
        } catch (DataAccessException ex) {
            throw new GuessPersistenceException("Could not access games for retrieval.");
        }
        return allGames;
    }

    @Override
    public List<Round> getGameRounds(int gameId) throws GuessPersistenceException {
        List<Round> allRounds = new ArrayList();
        try {
            allRounds = jdbc.query("SELECT * FROM rounds WHERE gameId = ? ORDER BY timeStamp", new roundMapper(), gameId);
        } catch (DataAccessException ex) {
            throw new GuessPersistenceException("Could not access rounds for retrieval.");
        }
        return allRounds;
    }

    @Override
    public boolean updateGameStatus(int gameId, boolean isDone) throws GuessPersistenceException {
        final String sql = "UPDATE games SET isGameOver = ? "
                + "WHERE gameId = ?;";
        boolean didPersist = false;
        try {
            didPersist = jdbc.update(sql, isDone, gameId) == 1;
        } catch (DataAccessException ex) {
            throw new GuessPersistenceException("Could not update status in games table.");
        }
        return didPersist;
    }

    @Override
    public int beginGame(Game toBegin) throws GuessPersistenceException {
        final String sql = "INSERT INTO games(isGameOver, targetNum) "
                + "VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbc.update((Connection conn) -> {
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                statement.setBoolean(1, toBegin.getIsGameOver());
                statement.setString(2, toBegin.getTargetNum());
                return statement;

            }, keyHolder);

            toBegin.setGameId(keyHolder.getKey().intValue());
        } catch (DataAccessException ex) {
            throw new GuessPersistenceException("Could not begin game.");
        }

        return toBegin.getGameId();
    }

    @Override
    public Round recordGuess(Round currentRound, Game currentGame) throws GuessPersistenceException {
        int currentRoundNumber = calculateNumberOfRounds(currentGame);
        currentRound.setRoundNumber(currentRoundNumber);
        
        java.sql.Timestamp today = getCurrentTimeStamp();
        final String sql = "INSERT INTO rounds(roundNumber, guessNumber, gameId, exactCorrect, partialCorrect, timeStamp) "
                + "VALUES(?,?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbc.update((Connection conn) -> {
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, currentRound.getRoundNumber());
                statement.setString(2, currentRound.getGuessNumber());
                statement.setInt(3, currentRound.getGameId());
                statement.setInt(4, currentRound.getExactCorrect());
                statement.setInt(5, currentRound.getPartialCorrect());
                statement.setTimestamp(6, today);
                return statement;

            }, keyHolder);

            currentRound.setRoundId(keyHolder.getKey().intValue());
            currentRound.setTimeStamp(today);
        } catch (DataAccessException ex) {
            throw new GuessPersistenceException("Could not persist current round data.");
        }
        return currentRound;
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    private static final class gameMapper implements RowMapper<Game> {

        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gme = new Game();
            gme.setGameId(rs.getInt("gameId"));
            gme.setIsGameOver(rs.getBoolean("isGameOver"));
            gme.setTargetNum(rs.getString("targetNum"));
            return gme;
        }
    }

    private static final class roundMapper implements RowMapper<Round> {

        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rnd = new Round();
            rnd.setRoundId(rs.getInt("roundId"));
            rnd.setRoundNumber(rs.getInt("roundNumber"));
            rnd.setGuessNumber(rs.getString("guessNumber"));
            rnd.setGameId(rs.getInt("gameId"));
            rnd.setExactCorrect(rs.getInt("exactCorrect"));
            rnd.setPartialCorrect(rs.getInt("partialCorrect"));
            rnd.setTimeStamp(rs.getTimestamp("timeStamp"));
            return rnd;
        }
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
