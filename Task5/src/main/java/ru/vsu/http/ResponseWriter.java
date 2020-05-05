package ru.vsu.http;

import ru.vsu.http.entities.Response;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    public static final int OFF = 0;
    private int bufferSize;
    private OutputStream outputStream;
    private byte[] buffer;

    public ResponseWriter(int bufferSize, OutputStream outputStream) {
        this.bufferSize = bufferSize;
        this.outputStream = outputStream;
        this.buffer = new byte[this.bufferSize];
    }

    public void write(Response response) throws IOException {
        outputStream.write(response.headersToBytes());
        if (response.getBody() == null) {
            return;
        }

        try(BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(response.getBody()), bufferSize)) {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, OFF, len);
            }
        }
    }
}
