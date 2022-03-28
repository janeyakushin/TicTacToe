/*
    DOM-реализация парсера для чтения xml-файла
    Этот класс предоставляет API для чтения из xml-файла
    данных о прошедших играх.
 */

package ru.yajaneya.tictactoe.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.yajaneya.tictactoe.Parser.pozitionAdapter.*;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DomReaderParserXml implements ReaderParser {
    File file;
    Document document;
    PozitionAdapter pozitionAdapter;

    @Override
    public boolean init(File file) {
        this.file = file;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            return choiceTypePozition();
        } catch ( ParserConfigurationException | SAXException | IOException e) {
            return false;
        }
    }

    private boolean choiceTypePozition() {
        System.out.println("Поддерживаются следующие форматы записи координат в файле истории игры: ");
        System.out.println("1 - x, y");
        System.out.println("2 - x y");
        System.out.println("3 - xy (размер поля не более 9х9)");
        System.out.println("4 - нумерация ячеек в следующем виде: ");
        System.out.println("    1 2 3 ");
        System.out.println("    4 5 6 ");
        System.out.println("    7 8 9 ");
        System.out.println("5 - нет нужного формата");
        System.out.println("---------------------------------------");
        System.out.println("Укажите нужный формат: ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case (1):
                pozitionAdapter = new PozitionOne();
                return true;
            case (2):
                pozitionAdapter = new PozitionTwo();
                return true;
            case (3):
                pozitionAdapter = new PozitionThree();
                return true;
            case (4):
                pozitionAdapter = new PozitionFour();
                return true;
            case (5):
                System.out.println("Обратитесь к разработчику для добавления соответствующего модуля.");
                return false;
            default:
                System.out.println("Такого варианта нет.");
                System.out.println("Обратитесь к разработчику для добавления соответствующего модуля.");
                return false;
        }
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("Player")) {
                players.add(getPlayer(node));
            }
        }
        return players;
    }

    @Override
    public List<Step> getSteps() {
        int num = 0, playerId = 0, x, y;
        List<Step> steps = new ArrayList<>();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("Step");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NamedNodeMap attributes = node.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                Node attr = attributes.item(j);
                if (attr.getNodeName().equals("num")) {
                    num = Integer.parseInt(attr.getNodeValue());
                }
                if (attr.getNodeName().equals("playerId")) {
                    playerId = Integer.parseInt(attr.getNodeValue());
                }
            }

            try {
                int [] pozition = pozitionAdapter.getPozition(node.getTextContent());
                x = pozition[0];
                y = pozition[1];
            } catch (NumberFormatException e) {
                System.out.println("Выбран неправильный формат записи координат в файле истории игры.");
                return null;
            }

            steps.add(new Step(num, playerId, x, y));

        }
        return steps;
    }

    @Override
    public Player getResult() {
        try {
            Node node = document.getDocumentElement()
                    .getElementsByTagName("GameResult").item(0)
                    .getChildNodes().item(0);
            return getPlayer(node);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Player getPlayer (Node node) {
        int id = 0;
        String name = "";
        char symbol = 0;
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equals("id")) {
                id = Integer.parseInt(attr.getNodeValue());
            }
            if (attr.getNodeName().equals("name")) {
                name = attr.getNodeValue();
            }
            if (attr.getNodeName().equals("symbol")) {
                symbol = attr.getNodeValue().toCharArray()[0];
            }
        }
        return new Player(id, name, symbol);
    }
}
