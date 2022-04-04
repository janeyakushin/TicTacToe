package ru.yajaneya.tictactoe.repositors;

import ru.yajaneya.tictactoe.config.Config;
import ru.yajaneya.tictactoe.models.Player;
import ru.yajaneya.tictactoe.models.Step;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Connection connection;
    private static Statement stmt;
    private int step;

    public DbManager() {
        initDb();
    }

    public boolean initDb () {
        try {
            connection = DriverManager.getConnection(Config.URL_DB_CONNECTION);
            stmt = connection.createStatement();
            step = 1;
            chekedAndcreateTables();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void desposeDb() throws SQLException {
        stmt.close();
        connection.close();
    }

    public Player addPlayer(String name) throws SQLException {
        Player player = new Player();
        ResultSet rs = stmt.executeQuery("SELECT * FROM players WHERE name = '" + name + "';");
        boolean key = rs.next();
        if (!key) {
            stmt.executeUpdate("INSERT INTO players (name) VALUES ('" + name + "');");
            rs = stmt.executeQuery("SELECT * FROM players WHERE name = '" + name + "';");
        }
        player.setId(rs.getInt("id"));
        player.setName(rs.getString("name"));
        return player;
    }

    public int addGame (Player player1, Player player2) throws SQLException {
        int idGame = getIdGame();
        int playeroneId = player1.getId();
        char symbolone = player1.getSymbol();
        int playertwoId = player2.getId();
        char symboltwo = player2.getSymbol();
        int number = getNumberGame(player1, player2);
        String str = "INSERT INTO games " +
                "(id, playerone_id, symbolone, playertwo_id, symboltwo, number_game) " +
                "VALUES (" + idGame + ", " + playeroneId + ", '" + symbolone + "', " + playertwoId +
                ", '" + symboltwo + "', " + number + ");";
        System.out.println("Возврат добавления игры: " + stmt.executeUpdate(str));

        return idGame;
    }

    public void addStep (int idGame, Player player, int x, int y) throws SQLException {
        String str = "INSERT INTO steps (game_id, player_id, number, x, y) VALUES (" +
                idGame + ", " + player.getId() + ", " + step++ + ", " + x + ", " + y + ");";
        System.out.println(str);
        stmt.executeUpdate(str);
    }

    public void addWin (int idGame, Player player) throws SQLException {
        int idWinPlayer;
        if (player == null) {
            idWinPlayer = 0;
        } else {
            idWinPlayer = player.getId();
        }
        stmt.executeUpdate("UPDATE games SET playerwin_id = " + idWinPlayer +
                " WHERE id = " + idGame);
    }

    public void updateRatingForPlayer(String name) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT rating FROM players WHERE name = '" + name + "';");
        int rating = rs.getInt("rating") + 1;
        stmt.executeUpdate("UPDATE players SET rating = " + rating + " WHERE name ='" + name + "';");
    }

    public List<Player> getRating () throws SQLException {
        List<Player> players = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM players ORDER BY rating DESC");
        while (rs.next()) {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setName(rs.getString("name"));
            player.setRating(rs.getInt("rating"));
            players.add(player);
        }
        return players;
    }

    public List<String> getGames () throws SQLException {
        List<String> games = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT p1.name as name1, p2.name as name2, number_game FROM games " +
                "JOIN players AS p1 ON games.playerone_id = p1.id " +
                "JOIN players AS p2 ON games.playertwo_id = p2.id " +
                "ORDER BY p1.name, p2.name;");
        while (rs.next()) {
            String game = rs.getString("name1") + " - " +
                    rs.getString("name2") +
                    " -> игра №" + rs.getInt("number_game");
            games.add(game);
        }

        return games;
    }

    public int getGame (String nameGame) {
        int idPlayer1 = getIdPlayerByName(nameGame.split(" - ")[0]);
        int idPlayer2 = getIdPlayerByName(nameGame.split(" - ")[1].split(" -> ")[0]);
        int numberGame = Integer.parseInt(nameGame.split("№")[1]);
        String sql = "SELECT id FROM games WHERE playerone_id = " + idPlayer1 +
                " AND playertwo_id = " + idPlayer2 + " AND number_game = " + numberGame + ";";
        System.out.println(sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getInt("id");
        } catch (SQLException throwables) {
            return 0;
        }
    }

    public List<Player> getPlayers (int gameId) {
        List<Player> players = new ArrayList<>();
        players.add(getPlayerFromGame(gameId, "playerone_id", "symbolone"));
        players.add(getPlayerFromGame(gameId, "playertwo_id", "symboltwo"));
        return players;
    }

    public List<Step> getSteps (int gameId) {
        try {
            ResultSet rsSteps = stmt.executeQuery("SELECT * FROM steps WHERE game_id = " + gameId + ";");
            List<Step> steps = new ArrayList<>();
            while (rsSteps.next()) {
                Step step = new Step();
                step.setNumber(rsSteps.getInt("number"));
                step.setPlayerId(rsSteps.getInt("player_id"));
                step.setX(rsSteps.getInt("x"));
                step.setY(rsSteps.getInt("y"));
                steps.add(step);
            }
            return steps;
        } catch (SQLException throwables) {
            return null;
        }
    }

    public Player getWinPlayer (int gameId) {
        return getPlayerFromGame(gameId, "playerwin_id", "");
    }

    private Player getPlayerFromGame (int gameId, String fieldId, String fieldSymbol) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM games WHERE id = " + gameId + ";");
            Player player = new Player();
            int id = rs.getInt(fieldId);
            player.setId(id);
            if (!fieldSymbol.equals("")) {
                String symbol = rs.getString(fieldSymbol);
                player.setSymbol(symbol.charAt(0));
            }
            player.setName(getNamePlayerById(id));
            return player;
        } catch (SQLException throwables) {
            return null;
        }
    }

    private int getNumberGame (Player player1, Player player2) throws SQLException {
        ResultSet rsng = stmt.executeQuery("SELECT MAX(number_game) as num FROM games WHERE " +
                "playerone_id = " + player1.getId() + " AND playertwo_id = " + player2.getId());
        return rsng.getInt("num") + 1;
    }

    private int getIdGame () throws SQLException {
        ResultSet rsig = stmt.executeQuery("SELECT MAX(id) as idgame FROM games");
        return rsig.getInt("idgame") + 1;
    }

    private int getIdPlayerByName (String name) {
        ResultSet rsin = null;
        try {
            rsin = stmt.executeQuery("SELECT id FROM players WHERE name = '" + name + "';");
            return rsin.getInt("id");
        } catch (SQLException throwables) {
            return 0;
        }
    }

    private String getNamePlayerById (int id) {
        ResultSet rsni = null;
        try {
            rsni = stmt.executeQuery("SELECT name FROM players WHERE id = " + id + ";");
            return rsni.getString("name");
        } catch (SQLException throwables) {
            return null;
        }
    }


    private void chekedAndcreateTables() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS players (\n" +
                "    id     INTEGER      PRIMARY KEY\n" +
                "                        UNIQUE\n" +
                "                        NOT NULL,\n" +
                "    name   VARCHAR (50) NOT NULL,\n" +
                "    rating INTEGER\n" +
                ");");


        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS games (\n" +
                "    id           INTEGER PRIMARY KEY\n" +
                "                         NOT NULL\n" +
                "                         UNIQUE,\n" +
                "    playerone_id INTEGER NOT NULL\n" +
                "                         REFERENCES players (id),\n" +
                "    symbolone VARCHAR (1)  NOT NULL,\n" +
                "    playertwo_id INTEGER NOT NULL\n" +
                "                         REFERENCES players (id),\n" +
                "    symboltwo VARCHAR (1)  NOT NULL,\n" +
                "    number_game  INTEGER NOT NULL,\n" +
                "    playerwin_id INTEGER REFERENCES players (id) \n" +
                ");");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS steps (\n" +
                "    id        INTEGER PRIMARY KEY\n" +
                "                      UNIQUE\n" +
                "                      NOT NULL,\n" +
                "    game_id   INTEGER REFERENCES games (id) \n" +
                "                      NOT NULL,\n" +
                "    player_id INTEGER NOT NULL\n" +
                "                      REFERENCES players (id),\n" +
                "    number    INTEGER NOT NULL,\n" +
                "    x         INTEGER,\n" +
                "    y         INTEGER\n" +
                ");");
    }
}
