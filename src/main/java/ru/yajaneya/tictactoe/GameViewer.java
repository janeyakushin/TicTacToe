/*
Класс для воспроизведения хода игры, записанной в xml-файл.
По умолчанию открывается каталог хранения файлов,
записанной игры "arhiv" в корневой директории проекта.
Однако, при запуске дается возможность выбрать файл из любого места хранения,
доступного на текущем компьютере
 */

package ru.yajaneya.tictactoe;

import ru.yajaneya.tictactoe.Parser.ReaderParser;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class GameViewer {

    public static void main(String[] args) {

        JFileChooser fileopen = new JFileChooser("./arhiv/");
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
           File file = fileopen.getSelectedFile();
           viewer(file);
        }

    }

    public static void viewer(File file) {
        ReaderParser readerParser = new HistoryFabric().getReadParser(); //устанавливается парсес чтения
        if (!readerParser.init(file.getName())) return;
        List<Player> players = readerParser.getPlayers();
        List<Step> steps = readerParser.getSteps();
        if (steps == null) {
            return;
        }
        Player winPlayer = readerParser.getResult();
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        System.out.println("Игрок №" + player1.getId() + ": " + player1.getName()
                        + ", символ: " + player1.getSymbol());
        System.out.println("Игрок №" + player2.getId() + ": " + player2.getName()
                        + ", символ: " + player2.getSymbol());
        System.out.println("-----------------");
        System.out.println("*** ХОД ИГРЫ ***");
        System.out.println("-----------------");
        Field field = new Field();
        field.init();
        field.draw();
        for (Step step : steps) {
            field.move(
                    getPlayerById(players, step.getPlayerId()),
                    step.getX(),
                    step.getY()
            );
            field.draw();
        }
        System.out.println("**********************");
        System.out.println("*** РЕЗУЛЬТАТ ИГРЫ ***");
        System.out.println("**********************");
        if (winPlayer == null) {
            System.out.println("НИЧЬЯ!!!");
        } else {
            System.out.println("Победитель: " + winPlayer.getName());
        }
    }

    private static Player getPlayerById (List<Player> players, int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }
}
