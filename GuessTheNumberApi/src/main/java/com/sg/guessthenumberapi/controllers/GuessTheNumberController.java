/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberapi.controllers;

import com.sg.guessthenumberapi.data.GuessTheNumberDao;
import com.sg.guessthenumberapi.models.Game;
import com.sg.guessthenumberapi.models.Round;
import com.sg.guessthenumberapi.services.BeginGameResponse;
import com.sg.guessthenumberapi.services.DisplayGameByIdResponse;
import com.sg.guessthenumberapi.services.GuessTheNumberService;
import com.sg.guessthenumberapi.services.ListGamesResponse;
import com.sg.guessthenumberapi.services.ListRoundsResponse;
import com.sg.guessthenumberapi.services.MakeGuessResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author afahrenkamp
 */
@RestController
@RequestMapping("/api")
public class GuessTheNumberController {

    @Autowired
    private GuessTheNumberService service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        int toReturn;

        BeginGameResponse response = service.beginGame();

        if (!response.isSuccess()) {
            toReturn = Integer.MIN_VALUE;
        } else {
            toReturn = response.getBegunGame().getGameId();
        }
        return toReturn;
    }

    @PostMapping("/guess")
    public Round guess(@RequestBody Round toReturn) {
        MakeGuessResponse response = service.makeGuess(toReturn.getGuessNumber(), toReturn.getGameId());
        if (!response.isSuccess()) {
            toReturn = null;
        } else {
            toReturn = response.getCurrentRound();
        }
        return toReturn;
    }

    @GetMapping("/game")
    public List<Game> displayAllGames() {
        List<Game> toReturn = new ArrayList();
        ListGamesResponse response = service.getAllGames();
        if (!response.isSuccess()) {
            toReturn = null;
        } else {
            toReturn = response.getAllGames();
        }
        return toReturn;
    }

    @GetMapping("/game/{gameId}")
    public Game displayGameById(@PathVariable int gameId) {
        Game toReturn = new Game();
        DisplayGameByIdResponse response = service.getGameById(gameId);
        if (!response.isSuccess()) {
            toReturn = null;
        } else {
            toReturn = response.getToDisplay();
        }
        return toReturn;
    }

    @GetMapping("/round/{gameId}")
    public List<Round> displayRoundsOfGame(@PathVariable int gameId) {
        List<Round> toReturn = new ArrayList();
        ListRoundsResponse response = service.getGameRounds(gameId);
        if (!response.isSuccess()) {
            toReturn = null;
        } else {
            toReturn = response.getAllRounds();
        }
        return toReturn;
    }

}
