package ru.yajaneya.tictactoe.models;

import java.util.List;

public class Gameplay {
    private List<Player> players;
    private Game game;
    private GameResult gameResult;

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

}
