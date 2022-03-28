package ru.yajaneya.tictactoe.models;

public class RequestWin {
    public String data;
    public String playerWin;

    public RequestWin() {
    }

    public RequestWin(String data, String playerWin) {
        this.data = data;
        this.playerWin = playerWin;
    }

    public RequestWin(String data) {
        this.data = data;
    }

    public String getPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(String playerWin) {
        this.playerWin = playerWin;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
