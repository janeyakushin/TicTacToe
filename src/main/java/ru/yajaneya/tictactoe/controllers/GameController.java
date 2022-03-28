package ru.yajaneya.tictactoe.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yajaneya.tictactoe.Parser.WriterParser;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    private WriterParser writerParser;
    private Field field;
    private Player player1;
    private Player player2;

    @GetMapping
    public char[][] init () {
        field = new Field();
        return field.getField();
    }

    @GetMapping ("/players")
    public List<Player> getPlayers () {
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

    @GetMapping ("/win")
    public RequestWin isWin () {
        if (field.win(player1)) {
            field.addToRating(player1);
            writerParser.endGame(player1);
            return new RequestWin("win", player1.getName());
        }
        if (field.win(player2)) {
            field.addToRating(player2);
            writerParser.endGame(player2);
            return new RequestWin("win", player2.getName());
        }
        if (field.deadHeat()) {
            writerParser.endGame(null);
            return new RequestWin("deadHeat");
        }
        return new RequestWin("do");
    }

    @GetMapping ("/rating")
    public List<Player> getRating () {
        try {
            List<Player> players = field.getRating();
            players.forEach(player -> System.out.println(player));
            return field.getRating();
        } catch (IOException e) {
            return null;
        }
    }

    @PostMapping ("/players")
    public String [] setPlayers (@RequestParam String name1, @RequestParam String name2) {
       player1 = new Player(1, name1, 'X');
       player2 = new Player(2, name2, '0');
       String [] symbols = {"X", "0"};
       startGameXml();
       return symbols;
    }

    @PostMapping ("/step")
    public char[][] setStep (@RequestParam String name, @RequestParam int x, @RequestParam int y) {
        Player player = getPlayerByName(name);
        field.move(player, x, y);
        writerParser.stepGame(player, x, y);
        return field.getField();
    }

    private Player getPlayerByName (String name) {
        if (player1.getName().equals(name))
            return player1;
        if (player2.getName().equals(name))
            return player2;
        return null;
    }

    private void startGameXml () {
        writerParser = new HistoryFabric().getWriteParser();
        writerParser.init(player1.getName(), player2.getName());
        writerParser.startGame(player1, player2);
    }

}
