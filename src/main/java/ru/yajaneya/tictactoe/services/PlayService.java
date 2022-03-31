package ru.yajaneya.tictactoe.services;

import org.springframework.stereotype.Service;
import ru.yajaneya.tictactoe.Parser.ReaderParser;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayService {

    private Player player1;
    private Player player2;
    private List<Step> steps;
    private Field field;

    public List<String> getGames () {
        List<String> strings = new ArrayList<>();
        File folder = new File("./arhiv");
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            strings.add(file.getName());
        }
        return strings;
    }

    public boolean getGame (String game) {
        ReaderParser readerParser = new HistoryFabric().getReadParser(); //устанавливается парсес чтения
        if (!readerParser.init(new File("./arhiv/" + game)))
            return false;
        List<Player> players = readerParser.getPlayers();
        steps = readerParser.getSteps();
        if (steps == null) {
            return false;
        }
        player1 = players.get(0);
        player2 = players.get(1);
        return true;
    }

    public List<String> getPlayers () {
        List<String> players = new ArrayList<>();
        players.add(player1.getName() + " -> " + player1.getSymbol());
        players.add(player2.getName() + " -> " + player2.getSymbol());
        return players;
    }

    public char[][] init () {
        field = new Field();
        return field.getField();
    }

    public char[][] getStep (int step) {
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
