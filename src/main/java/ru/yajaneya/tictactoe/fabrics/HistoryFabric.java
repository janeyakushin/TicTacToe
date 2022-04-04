package ru.yajaneya.tictactoe.fabrics;

import ru.yajaneya.tictactoe.Parser.*;
import ru.yajaneya.tictactoe.config.Config;

public class HistoryFabric {

    public ReaderParser getReadParser() {
        switch (Config.FORMAT_DATA) {
            case "xml":
                return new DomReaderParserXml();
            case "json":
                return new ReaderParserJson();
            case "db":
                return new ReadParserDb();
            default:
                return null;
        }
    }

    public WriterParser getWriteParser() {
        switch (Config.FORMAT_DATA) {
            case "xml":
                return new FileWriterParserXml();
            case "json":
                return new WriterParserJson();
            case "db":
                return new WriteParserDb();
            default:
                return null;
        }
    }
}
