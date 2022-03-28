package ru.yajaneya.tictactoe.fabrics;

import ru.yajaneya.tictactoe.Parser.ReaderParser;
import ru.yajaneya.tictactoe.Parser.ReaderParserJson;
import ru.yajaneya.tictactoe.Parser.WriterParser;
import ru.yajaneya.tictactoe.Parser.WriterParserJson;

public class HistoryFabric {

    public ReaderParser getReadParser () {
//        return new DomReaderParserXml();
        return new ReaderParserJson();
    }

    public WriterParser getWriteParser () {
//        return new FileWriterParserXml();
        return new WriterParserJson();
    }
}
