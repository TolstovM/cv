package ru.vsu.http.handlers;

import ru.vsu.http.entities.Request;
import ru.vsu.http.exceptions.WrongHttpRequest;
import ru.vsu.http.utils.HttpUtils;
import ru.vsu.io.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestHandler {

    private static final int ITEM_IN_PROTOCOL_LINE = 3;
    private static final String PROTOCOL_REGEX = " ";
    private static final int METHOD_INDEX = 0;
    private static final int RESOURCE_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    private int bufferSize;

    public HttpRequestHandler(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Request getRequestFromInputStream(InputStream inputStream) throws IOException {
        inputStream = new BufferedInputStream(inputStream, bufferSize);
        String protocolLine = Utils.readLine(inputStream);
        String[] protocolData = protocolLine.split(PROTOCOL_REGEX, ITEM_IN_PROTOCOL_LINE);

        List<String> metadata = new ArrayList<>();
        String line = Utils.readLine(inputStream);
        while (!line.equals(HttpUtils.REQUEST_HEADERS_END_LINE)) {
            metadata.add(line);
            line = Utils.readLine(inputStream);
        }

        Request request =  new Request(protocolData[METHOD_INDEX],
                protocolData[RESOURCE_INDEX],
                protocolData[PROTOCOL_INDEX],
                inputStream,
                HttpUtils.getHeaders(metadata));
        HttpUtils.initCookiesInRequest(request);
        return request;
    }
}
