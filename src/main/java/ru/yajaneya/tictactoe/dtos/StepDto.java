package ru.yajaneya.tictactoe.dtos;

public class StepDto {
    private char[][] field;
    private String stepPlayerName;

    public char[][] getField() {
        return field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public String getStepPlayerName() {
        return stepPlayerName;
    }

    public void setStepPlayerName(String stepPlayerName) {
        this.stepPlayerName = stepPlayerName;
    }

}
