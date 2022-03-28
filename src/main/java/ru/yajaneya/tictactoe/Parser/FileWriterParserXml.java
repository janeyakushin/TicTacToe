/*
Этот класс предоставляет API для создания и заполнения xml-файла
данными о прошедших играх двух игроков до выхода из программы.
Класс выполнен средствами записи в файл.
Файлы именуются в виде game[name1][name2][X].xml,
где name1 - имя 1го игрока, name2 - имя второго игрока,
    X - номер партии в игре (берется следующий номер для этих игроков в каталоге).
Например, gameВасилийПетр1.xml
 */

package ru.yajaneya.tictactoe.Parser;

import ru.yajaneya.tictactoe.models.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileWriterParserXml implements WriterParser {
    private String outFile;
    private OutputStreamWriter fileWriter;
    private int step;

    @Override
    public boolean init(String name1, String name2) {
        this.outFile = outFile + ".xml";
        step = 1;
        try {
            fileWriter = new OutputStreamWriter(new FileOutputStream(
                    createFileName("game" + name1 + name2)),
                    "windows-1251");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private File createFileName(String outFile) {
        File folder = new File("." +
                File.separator + "arhiv");
        if (!folder.exists()) {
            folder.mkdir();
        }
        int count = 1;
        File file;
        do {
            file = new File("." + File.separator + "arhiv" + File.separator + outFile + count++ + ".xml");
        } while (file.exists());
        return file;
    }

    @Override
    public boolean startGame(Player player1, Player player2) {
        try {
            writeToFile("<?xml version=\"1.0\" encoding=\"windows-1251\"?>");
            writeToFile("<Gameplay>");
            writeToFile("   <Player id=\"" + player1.getId() + "\" name=\"" + player1.getName()
                    + "\" symbol=\"" + player1.getSymbol() + "\"/>");
            writeToFile("   <Player id=\"" + player2.getId() + "\" name=\"" + player2.getName()
                    + "\" symbol=\"" + player2.getSymbol() + "\"/>");
            writeToFile("   <Game>");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean stepGame(Player player, int x, int y) {
        try {
            writeToFile("       <Step num=\"" + step++ + "\" playerId=\"" + player.getId()
                    + "\">" + x + "," + y + "</Step>");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean endGame(Player player) {
        try {
            writeToFile("   </Game>");
            if (player == null) {
                writeToFile("   <GameResult>Draw!</GameResult>");
            } else {
                writeToFile("   <GameResult><Player id=\"" + player.getId()
                        + "\" name=\"" + player.getName() + "\" symbol=\""
                        + player.getSymbol() + "\"/></GameResult>");
            }
            writeToFile("</Gameplay>");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void writeToFile (String line) throws IOException{
        fileWriter.write(line + "\n");
    }

}

