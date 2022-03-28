/*
 Класс, описывающий ход (шаг) игры
 */

package ru.yajaneya.tictactoe.models;

public class Step {
    private int number; // номер хода
    private int playerId; // id игрока
    private int x; // номер строки хода
    private int y; // номер столбца хода

    public Step(int number, int playerId, int x, int y) {
        this.number = number;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }

    public Step() {
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Step{" +
                "number=" + number +
                ", playerId=" + playerId +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
