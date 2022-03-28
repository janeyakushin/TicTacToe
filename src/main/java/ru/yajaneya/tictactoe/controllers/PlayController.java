package ru.yajaneya.tictactoe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yajaneya.tictactoe.Parser.ReaderParser;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayController {
    private Player player1;
    private Player player2;
    private List<Step> steps;
    private Player winPlayer;
    private Field field;
    private int step;

    @GetMapping("/games")
    public List<String> getGames () {
        List<String> strings = new ArrayList<>();
        File folder = new File("./arhiv");
        File[] files = folder.listFiles();
        for (File file : files) {
            strings.add(file.getName());
        }
        return strings;
    }

    @GetMapping ("/game")
    public boolean getGame (@RequestParam String game) {
        ReaderParser readerParser = new HistoryFabric().getReadParser(); //устанавливается парсес чтения
        if (!readerParser.init(new File("./arhiv/" + game)))
            return false;
        List<Player> players = readerParser.getPlayers();
        steps = readerParser.getSteps();
        if (steps == null) {
            return false;
        }
        winPlayer = readerParser.getResult();
        player1 = players.get(0);
        player2 = players.get(1);
        return true;
    }

    @GetMapping ("/game/players")
    public List<String> getPlayers () {
        List<String> players = new ArrayList<>();
        players.add(player1.getName() + " -> " + player1.getSymbol());
        players.add(player2.getName() + " -> " + player2.getSymbol());
        return players;
    }

    @GetMapping ("/game/init")
    public char[][] init () {
        field = new Field();
        return field.getField();
    }

    @GetMapping ("/game/step")
    public char[][] getStep (@RequestParam int step) {
        if (step > steps.size()) {
            char[][] c = new char[1][1];
            c[0][0] = 'q';
            return c;
        }
        Step nextStep = steps.get(step-1);
        field.move(getPlayerById(nextStep.getPlayerId()), nextStep.getX(), nextStep.getY());
        return field.getField();
    }

    private Player getPlayerById (int id) {
        if (player1.getId() == id)
            return player1;
        if (player2.getId() == id)
            return player2;
        return null;
    }


}
