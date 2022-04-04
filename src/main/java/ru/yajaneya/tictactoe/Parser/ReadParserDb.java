package ru.yajaneya.tictactoe.Parser;

import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;
import ru.yajaneya.tictactoe.repositors.DbManager;

import java.sql.SQLException;
import java.util.List;

public class ReadParserDb implements ReaderParser{
    DbManager dbManager = new DbManager();
    private int gameId;

    @Override
    public List<String> getGames() {
        try {
            return dbManager.getGames();
        } catch (SQLException throwables) {
            return null;
        }
    }

    @Override
    public boolean init(String nameGame) {
        gameId = dbManager.getGame(nameGame);
        return gameId != 0;
    }

    @Override
    public List<Player> getPlayers() {
        return dbManager.getPlayers(gameId);
    }

    @Override
    public List<Step> getSteps() {
        return dbManager.getSteps(gameId);
    }

    @Override
    public Player getResult() {
        return dbManager.getWinPlayer(gameId);
    }
}
