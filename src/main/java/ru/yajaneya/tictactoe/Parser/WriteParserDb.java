package ru.yajaneya.tictactoe.Parser;

import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.repositors.DbManager;

import java.sql.SQLException;

public class WriteParserDb implements WriterParser{
    DbManager dbManager = new DbManager();
    int idGame;

    @Override
    public boolean init(String name1, String name2) {
        return true;
    }

    @Override
    public boolean startGame(Player player1, Player player2) {
        try {
            player1.setId(dbManager.addPlayer(player1.getName()).getId());
            player2.setId(dbManager.addPlayer(player2.getName()).getId());
            idGame = dbManager.addGame(player1, player2);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public boolean stepGame(Player player, int x, int y) {
        try {
            dbManager.addStep(idGame, player, x, y);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public boolean endGame(Player player) {
        try {
            dbManager.addWin(idGame, player);
            if (player !=null) {
                dbManager.updateRatingForPlayer(player.getName());
            }
            dbManager.desposeDb();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }
}
