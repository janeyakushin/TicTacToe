package ru.yajaneya.tictactoe.models;

public class GameResult {
    private Player playerWin;
    public static boolean endGame;

    public GameResult(Player playerWin) {
        this.playerWin = playerWin;
    }

    public GameResult() {
    }

    public Player getPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(Player playerWin) {
        this.playerWin = playerWin;
    }
}
