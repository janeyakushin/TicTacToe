/*
Этот класс предоставляет API для создания и заполнения json-файла
данными о прошедших играх двух игроков до выхода из программы.
Класс выполнен средствами записи в файл.
Файлы именуются в виде game[name1][name2][X].json,
где name1 - имя 1го игрока, name2 - имя второго игрока,
    X - номер партии в игре (берется следующий номер для этих игроков в каталоге).
Например, gameВасилийПетр1.json
 */


package ru.yajaneya.tictactoe.Parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.yajaneya.tictactoe.models.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WriterParserJson implements WriterParser {
    private String jsonStr;
    private String outFile;
    private Gameplay gameplay;
    private List<Step> steps;
    private ObjectMapper mapper;
    private int step;

    @Override
    public boolean init(String name1, String name2) {
        this.outFile = "game" + name1 + name2;
        mapper = new ObjectMapper();
        steps = new ArrayList<>();
        gameplay = new Gameplay();
        return true;
    }

    @Override
    public boolean startGame(Player player1, Player player2) {
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameplay.setPlayers(players);
        return true;
    }

    @Override
    public boolean stepGame(Player player, int x, int y) {
        steps.add(new Step(step++, player.getId(), x, y));
        return true;
    }

    @Override
    public boolean endGame(Player player) {
        Game game = new Game(steps);
        GameResult gameResult = new GameResult(player);
        gameplay.setGame(game);
        gameplay.setGameResult(gameResult);
        JsonGamePlay jsonGamePlay = new JsonGamePlay(gameplay);
        try {
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonGamePlay);
            return writeToFile(jsonStr);
        } catch (JsonProcessingException e) {
            return false;
        }

    }

    private boolean writeToFile(String jsonStr) {
        try {
            makeArhivDir();
            int count = 1;
            File file;
            do {
                file = new File("." + File.separator + "arhiv" + File.separator + outFile + count++ + ".json");
            } while (file.exists());
            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
                    file),"windows-1251");
            fileWriter.write(jsonStr);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void makeArhivDir() {
        File folder = new File("." +
                File.separator + "arhiv");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
