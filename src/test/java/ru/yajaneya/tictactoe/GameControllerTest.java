package ru.yajaneya.tictactoe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yajaneya.tictactoe.config.Config;
import ru.yajaneya.tictactoe.controllers.GameController;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.RequestWin;
import ru.yajaneya.tictactoe.services.GameService;

@SpringBootTest
public class GameControllerTest {
    @Autowired
    private GameController gameController;
    @Autowired
    private GameService gameService;

    @Test
    // Проверка инициализации игрового поля
    public void initFieldTest () {
        char[][] fieldForTest = gameController.init();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(Config.SYMBOL_FIELD, fieldForTest[i][j]);
            }
        }
    }

    @Test
    // Проверка обнаружения победы
    public void isWinTest () {
        Field field = new Field();
        char[][] fieldForTest;
        // тест по строкам
        for (int i = 0; i < 3; i++) {
            fieldForTest = gameController.init();
            for (int j = 0; j < 3; j++) {
                fieldForTest[i][j] = 'X';
            }
            testWinOnField();
        }
        // тест по столбцам
        for (int i = 0; i < 3; i++) {
            fieldForTest = gameController.init();
            for (int j = 0; j < 3; j++) {
                fieldForTest[j][i] = 'X';
            }
            testWinOnField();
        }
        // тест по прямой диагонали
        fieldForTest = gameController.init();
        for (int i = 0; i < 3; i++) {
            fieldForTest[i][i] = 'X';
        }
        testWinOnField();
        // тест по обратной диагонали
        fieldForTest = gameController.init();
        for (int i = 0; i < 3; i++) {
            fieldForTest[2-i][i] = 'X';
        }
        testWinOnField();
    }

    @Test
    // Проверка отправки имен игроков
    public void setPlayersTest () {
        gameController.setPlayers("Василий", "Петр");
        Assertions.assertEquals(gameService.getPlayer1().getName(), "Василий");
        Assertions.assertEquals(gameService.getPlayer2().getName(), "Петр");
    }

    @Test
    // Проверка отправки игрового хода
    public void setStepTest () {
        gameController.setStep("Василий", 2, 1);
        char symbol = gameService.getPlayer1().getSymbol();
        Assertions.assertEquals(symbol, gameService.getField().getField()[1][0]);
    }


    private void testWinOnField() {
        Player player1 = new Player(1, "Василий", 'X');
        Player player2 = new Player(2, "Петр", '0');
        gameController.setPlayers(player1.getName(), player2.getName());
        RequestWin rw = new RequestWin("win", "Василий");
        RequestWin rw1 = gameController.isWin();
        Assertions.assertTrue(
                rw.getData().equals(rw1.getData()) &
                        rw.getPlayerWin().equals(rw1.getPlayerWin()));
    }

}
