/*
Интерфейс, описывающий API для чтения из xml-файла
данных об прошедших играх двух игроков.
 */

package ru.yajaneya.tictactoe.Parser;

import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import java.io.File;
import java.util.List;

public interface ReaderParser {
    boolean init(File file);
    List<Player> getPlayers ();
    List<Step> getSteps();
    Player getResult();
}
