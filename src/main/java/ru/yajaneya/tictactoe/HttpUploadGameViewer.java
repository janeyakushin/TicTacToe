package ru.yajaneya.tictactoe;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class HttpUploadGameViewer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        server.createContext("/gameview", new TestHandler());

        server.start();
    }

    static  class TestHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange exchange) throws IOException {

            InputStream inputStream = exchange.getRequestBody();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String jsonStr = "";
            while ((line = bufferedReader.readLine()) != null) {
                jsonStr += line;
            }

            String fileName = jsonStr.split("filename=\"")[1];
            fileName = fileName.split("\"")[0];

            jsonStr = jsonStr.split("application/json")[1];
            jsonStr = jsonStr.split("------")[0];

            String f = writeToFile(jsonStr, fileName);

            GameViewer.viewer(new File(f));

        }

    }

    private static String writeToFile(String jsonStr, String outFile) {
        try {
            File folder = new File("." +
                    File.separator + "arhiv");
            if (!folder.exists()) {
                folder.mkdir();
            }
            String fileName;
            File file;
            int count = 1;
            do {
                fileName = "." + File.separator + "arhiv" + File.separator + count++ + "_" + outFile;
                file = new File(fileName);
            } while (file.exists());
            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
                    file),"windows-1251");
            fileWriter.write(jsonStr);
            fileWriter.close();
            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

}
