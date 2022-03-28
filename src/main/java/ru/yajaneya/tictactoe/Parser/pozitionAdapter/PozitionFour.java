/*
Работа с указанием пронумерованной позиции
1 2 3
4 5 6
7 8 9
 */

package ru.yajaneya.tictactoe.Parser.pozitionAdapter;

public class PozitionFour implements PozitionAdapter {

    @Override
    public int[] getPozition(String content) {
        int pozition = Integer.parseInt(content);
        int [] rezult = new int[2];
        if (pozition >= 1 && pozition <=3) {
            rezult[0] = 1;
        }
        if (pozition >= 4 && pozition <=6) {
            rezult[0] = 2;
        }
        if (pozition >= 7 && pozition <=9) {
            rezult[0] = 3;
        }
        if (pozition == 1 || pozition == 4 || pozition == 7) {
            rezult[1] = 1;
        }
        if (pozition == 2 || pozition == 5 || pozition == 8) {
            rezult[1] = 2;
        }
        if (pozition == 3 || pozition == 6 || pozition == 9) {
            rezult[1] = 3;
        }
        return rezult;
    }
}
