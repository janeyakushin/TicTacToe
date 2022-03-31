package ru.yajaneya.tictactoe.services;

import org.springframework.stereotype.Service;
import ru.yajaneya.tictactoe.Parser.WriterParser;
import ru.yajaneya.tictactoe.dtos.StepDto;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.RequestWin;

import java.io.IOException;
import java.util.List;

@Service
public class GameService {
    private WriterParser writerParser;
    private Field field;
    private Player player1;
    private Player player2;

    public char[][] init () {
        field = new Field();
        return field.getField();
    }

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

    public List<Player> getRating () {
        try {
            List<Player> players = field.getRating();
            return field.getRating();
        } catch (IOException e) {
            return null;
        }
    }

    public String [] setPlayers (String name1, String name2) {
        if (name1.equals(name2)) {
            return new String[]{"double"};
        }
        player1 = new Player(1, name1, 'X');
        player2 = new Player(2, name2, '0');
        String [] symbols = {"X", "0"};
        startGameXml();
        return symbols;
    }

    public StepDto setStep (String name, int x, int y) {
        StepDto stepDto = new StepDto();
        if (field.getField()[x-1][y-1] != '-') {
            stepDto.setField(field.getField());
            stepDto.setStepPlayerName(name);
            return stepDto;
        }
        Player player = getPlayerByName(name);
        field.move(player, x, y);
        writerParser.stepGame(player, x, y);
        stepDto.setField(field.getField());
        stepDto.setStepPlayerName(getAnotherPlayerName(name));
        return stepDto;
    }

    private Player getPlayerByName (String name) {
        if (player1.getName().equals(name))
            return player1;
        if (player2.getName().equals(name))
            return player2;
        return null;
    }

    private String getAnotherPlayerName (String name) {
        if (player1.getName().equals(name))
            return player2.getName();
        if (player2.getName().equals(name))
            return player1.getName();
        return null;
    }

    private void startGameXml () {
        writerParser = new HistoryFabric().getWriteParser();
        writerParser.init(player1.getName(), player2.getName());
        writerParser.startGame(player1, player2);
    }

}
