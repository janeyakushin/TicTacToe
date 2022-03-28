/*
Работа с координатами в виде xy для поля размером до 9х9
 */

package ru.yajaneya.tictactoe.Parser.pozitionAdapter;

public class PozitionThree implements PozitionAdapter {

    @Override
    public int[] getPozition(String content) {
        int [] rezult = new int[2];
        rezult [0] = Integer.parseInt(String.valueOf(content.charAt(0)));
        rezult [1] = Integer.parseInt(String.valueOf(content.charAt(1)));
        return rezult;
    }
}
