package ru.yajaneya.tictactoe.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные о завершении игры")
public class RequestWin {
    @Schema(description = "Данные о типе завершения: win - победа, deadHeat - ничья, do - в процессе игры", required = true, example = "do")
    public String data;
    @Schema(description = "Имя победителя", example = "Василий")
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
