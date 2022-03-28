/*
Дополнительное задание:

Реализовать простую игру крестики-нолики с вводом с клавиатуры
Через ввод с клавиатуры вводим имена игроков
Имеется матрица 3 x 3 - игровое поле,
| - | - | - |
| - | - | - |
| - | - | - |
два игрока, по очереди совершают ход, на каждом шаге происходит проверка на победителя,
и предложение сыграть еще раз!
1ый игрок всегда X(крестик), 2ой игрок 0(нолик)
| - | - | 0 |
| - | x | - |
| x | - | 0 |
Вести рейтинг участников в файле.
 */



package ru.yajaneya.tictactoe;
import ru.yajaneya.tictactoe.Parser.WriterParser;
import ru.yajaneya.tictactoe.fabrics.HistoryFabric;
import ru.yajaneya.tictactoe.models.Field;
import ru.yajaneya.tictactoe.models.Player;

import java.util.Scanner;

public class TicTacToe {
    private static int countGames = 1;
    private static Player player1;
    private static Player player2;
    private static WriterParser writerParser;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Первый игрок, введите своё имя: ");
        player1 = new Player(1, scanner.nextLine(),'х');
        System.out.print("Второй игрок, введите своё имя: ");
        player2 = new Player(2, scanner.nextLine(), '0');
        startGameXml();
        Field field = new Field();

        while (true) {
            while (true) {
                field.draw();
                if (step(scanner, field, player1)) {
                    win(player1, field);
                    writerParser.endGame(player1);
                    break;
                }
                if (field.deadHeat()) {
                    deadHeat();
                    field.draw();
                    writerParser.endGame(null);
                    break;
                }
                field.draw();
                if (step(scanner, field, player2)) {
                    win(player2, field);
                    writerParser.endGame(player2);
                    break;
                }
                if (field.deadHeat()) {
                    deadHeat();
                    field.draw();
                    writerParser.endGame(null);
                    break;
                }
            }
            System.out.println();
            System.out.print("Сыграем ещё раз? (да/нет): ");
            String  answer = scanner.nextLine();
            while (!answer.equals("да") && !answer.equals("нет")) {
                answer = scanner.nextLine();
            }
            if (answer.equals("да")) {
                field.init();
                startGameXml();
                continue;
            }
            System.out.println("*****************");
            System.out.println("*-Конец игры!!!-*");
            System.out.println("*****************");
            break;
        }
    }

    private static void startGameXml () {
        writerParser = new HistoryFabric().getWriteParser();
        writerParser.init(player1.getName(), player2.getName());
        writerParser.startGame(player1, player2);
    }

    private static void deadHeat() {
        System.out.println("**************");
        System.out.println("*** НИЧЬЯ! ***");
        System.out.println("**************");
    }

    private static void win(Player player, Field field) {
        System.out.println("**** **** ****");
        System.out.println(player.getName() + " ПОБЕДИЛ !!!");
        System.out.println("**** **** ****");
        field.addToRating(player);
        field.draw();
        field.printRating();
    }

    private static boolean step (Scanner scanner, Field field, Player player) {
        System.out.println("--- --- ---");
        System.out.println(player.getName() + " ходит:");
        int x, y;
        do {
            System.out.print("Введите координату x: ");
            x = getValue(scanner);
        } while (x == -1);
        do {
            System.out.print("Введите координату y: ");
            y = getValue(scanner);
        } while (y == -1);
        if (!field.move(player, x, y)) {
            System.out.println("Ячейка занята или лежит за пределами поля. Повторите ход...");
            step(scanner, field, player);
        } else {
            writerParser.stepGame(player, x, y);
        }
        return field.win(player);
    }

    private static int getValue (Scanner scanner) {
        try {
            int s = Integer.parseInt(scanner.nextLine());
            if (s < 1) {
                return -1;
            } else {
                return s;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
