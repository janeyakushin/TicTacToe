package ru.yajaneya.tictactoe.models;

public class JsonGamePlay {
    Gameplay gameplay;

    public JsonGamePlay() {
    }

    public JsonGamePlay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    public Gameplay getGameplay() {
        return gameplay;
    }

    public void setGameplay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

}
