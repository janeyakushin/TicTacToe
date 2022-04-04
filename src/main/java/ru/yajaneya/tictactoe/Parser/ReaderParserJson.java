/*
    Реализация парсера для чтения json-файла
    Этот класс предоставляет API для чтения из json-файла
    данных о прошедших играх.
 */


package ru.yajaneya.tictactoe.Parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.yajaneya.tictactoe.models.JsonGamePlay;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderParserJson implements ReaderParser{
    private File file;
    private String jsonStr;
    private JsonGamePlay jsonGamePlay;

    @Override
    public List<String> getGames() {
        List<String> strings = new ArrayList<>();
        File folder = new File("./arhiv");
        File[] files = folder.listFiles(new FileExtFilter(".json"));
        if (files == null) {
            return null;
        }
        for (File file : files) {
            strings.add(file.getName());
        }
        return strings;
    }

    @Override
    public boolean init(String nameGame) {
        this.file = new File("./arhiv/" + nameGame);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "windows-1251");
            BufferedReader fileReader = new BufferedReader(inputStreamReader);
            String line = "";
            jsonStr = "";
            while ((line = fileReader.readLine()) != null) {
                jsonStr += line;
            }
            ObjectMapper mapper = new ObjectMapper();
            jsonGamePlay = new JsonGamePlay();
            jsonGamePlay = mapper.readValue(jsonStr, JsonGamePlay.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Player> getPlayers() {
        return jsonGamePlay.getGameplay().getPlayers();
    }

    @Override
    public List<Step> getSteps() {
        return jsonGamePlay.getGameplay().getGame().getSteps();
    }

    @Override
    public Player getResult() {
        return jsonGamePlay.getGameplay().getGameResult().getPlayerWin();
    }

    private class FileExtFilter implements FilenameFilter{

        private String ext;

        public FileExtFilter(String ext){
            this.ext = ext.toLowerCase();
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }
}
