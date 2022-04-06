package ru.yajaneya.tictactoe.config;

public class Config {
//    public static final String FORMAT_DATA = "json"; // формат сохранения данных игры
//    public static final String FORMAT_DATA = "xml"; // формат сохранения данных игры
    public static final String FORMAT_DATA = "db"; // формат сохранения данных игры
    public static final String URL_DB_CONNECTION = "jdbc:sqlite:gamedb.db"; // подключение к БД
    public static final String SYMBOL_WALL = "|"; // символ, обозначающий стенку
    public static final char SYMBOL_FIELD = '-'; // символ, обозначающий пустое поле
    public static final String RATING_FILE = "rating.txt"; //файл рейтинга игроков
    public static final String ARHIV_DIR = "./arhiv/"; //директория для хранения архива игр

}
