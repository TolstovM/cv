package ru.vsu.io;

import java.io.IOException;
import java.io.InputStream;

public final class Utils {

    private Utils() {}

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int symbol = inputStream.read();
        while (symbol != -1 && (char)symbol !='\n') {
            line.append((char) symbol);
            symbol = inputStream.read();
        }
        if (symbol != -1) {
            line.append('\n');
        }
        return line.toString();
    }
}
