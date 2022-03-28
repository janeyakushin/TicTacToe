/*
Класс предоставляет API игрового поля игры "Крестики-нолики"
 */

package ru.yajaneya.tictactoe.models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Field {
    private final String SYMBOL_WALL = "|"; // символ, обозначающий стенку
    private final char SYMBOL_FIELD = '-'; // символ, обозначающий пустое поле
    private final String RATING_FILE = "rating.txt"; //файл рейтинга игроков
    private int dotsToWin; // количество символов для победы
    private int fieldSize; // размер поля
    private char [][] field; // поле

    // вариант по умолчанию
    public Field() {
        dotsToWin = 3;
        fieldSize = 3;
        field = new char[fieldSize][fieldSize];
        init();
    }

    public char[][] getField() {
        return field;
    }

    // возможность для расширения игры
    public Field(int dotsToWin, int fieldSize) {
        this.dotsToWin = dotsToWin;
        this.fieldSize = fieldSize;
        this.field = new char[fieldSize][fieldSize];
        init();
    }

    public void init () {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = SYMBOL_FIELD;
            }
        }
    }

    public void draw () {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(SYMBOL_WALL + field[i][j]);
            }
            System.out.println(SYMBOL_WALL);
        }
        System.out.println();
    }

    public boolean move (Player player, int x, int y) {
        if (x < 1 || x > fieldSize || y < 1 || y > fieldSize) {
            return false;
        }
        if (field[x-1][y-1] != '-') {
            return false;
        }
        field[x-1][y-1] = player.getSymbol();
        return true;
    }

    public boolean deadHeat () {
        for (int i = 0; i < fieldSize; i ++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == SYMBOL_FIELD)
                    return false;
            }
        }
        return true;
    }

    /*
      Метод проверки универсален для различных размеров поля и различного количества символов для победы.
      Взят с моих прошлых наработок.
     */
    public boolean win (Player player) {
        char sumb = player.getSymbol();
        int count;
        for (int i = 0; i< fieldSize; i++) {
            for (int j = 0; j< fieldSize; j++) {
                if (field[i][j] == sumb) {

                    // проверка по вертикали
                    count = 1;
                    for (int k = i+1; k< fieldSize; k++) {
                        if ((field[k][j] == sumb) & (field[k-1][j] == sumb)) count +=1;
                    }
                    if (count >= dotsToWin) return true;

                    // проверка по горизонтали
                    count = 1;
                    for (int k = j+1; k< fieldSize; k++) {
                        if ((field[i][k] == sumb) & (field[i][k-1] == sumb)) count +=1;
                    }
                    if (count >= dotsToWin) return true;

                    // проверка по диагонали вперед
                    count = 1;
                    for (int k = 1; (i+k< fieldSize)&(j+k< fieldSize); k++) {
                        if ((field[i+k][j+k] == sumb) & (field[i+k-1][j+k-1] == sumb)) count +=1;
                    }
                    if (count >= dotsToWin) return true;

                    // проверка по диагонали назад
                    count = 1;
                    for (int k = 1; (i+k< fieldSize)&(j-k>=0); k++) {
                        if ((field[i+k][j-k] == sumb) & (field[i+k-1][j-k+1] == sumb)) count +=1;
                    }
                    if (count >= dotsToWin) return true;

                }
            }
        }
        return false;
    }

    public boolean addToRating (Player player) {
        File file = new File("." + File.separator + RATING_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            List<Player> rating = getRating(file);
            boolean addRatig = false;
            for (Player pl : rating) {
                if (pl.getName().equals(player.getName())) {
                    pl.setRating(pl.getRating() + 1);
                    addRatig = true;
                }
            }
            if (!addRatig) {
                rating.add(new Player(1, player.getName()));
            }
           putRating(file, sortRating(rating));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

     public boolean printRating () {
         System.out.println("Рейтинг игроков!!!");
         System.out.println("-------------------");
         File file = new File("." + File.separator + RATING_FILE);
         try {
             List<Player> players = getRating(file);
             for (Player player : players) {
                 System.out.println((player.getName() + " " + player.getRating()));
             }
         } catch (IOException e) {
             return false;
         }
         System.out.println("-------------------");
         return true;
    }

    public List<Player> getRating () throws IOException {
        File file = new File("." + File.separator + RATING_FILE);
        return getRating(file);
    }

    private List<Player> getRating (File file) throws IOException {
        List<Player> players = new ArrayList<>();
        FileReader fileReader = new FileReader(file);
        Scanner scan = new Scanner(fileReader);
        while (scan.hasNextLine()) {
            String [] str = scan.nextLine().split(" ");
            players.add(new Player(Integer.parseInt(str[1]), str[0]));
        }
        fileReader.close();
        return players;
    }

    private void putRating (File file, List<Player> list) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (Player player : list) {
            fileWriter.write(player.getName() + " " + player.getRating() + "\n");
        }
        fileWriter.close();
    }

    private List sortRating(List<Player> list) throws IOException {
        list.sort(Player.COMPARE_BY_RATING);
        return list;
    }

}
