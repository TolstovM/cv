package ru.vsu.http.handlers;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.http.entities.Request;
import ru.vsu.http.entities.Response;

import java.io.File;

import java.io.FileInputStream;
import java.util.UUID;

public class HttpHandler {

    private static final String UUID_KEY = "uuid";
    private static final String PROTOCOL = "Http/1.1";
    private static final int OK_STATUS = 200;
    private static final String OK_STATUS_MESSAGE = "OK";
    private static final int NOT_FOUND_STATUS = 404;
    private static final String NOT_FOUND_STATUS_MESSAGE = "Not Found";
    public static final String FILE_NOT_FOUND_PAGE = "404Page.html";

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    public static final String PAGE_DOES_NOT_FOUND = "File not found page does not found";

    private String folderPath;

    public String getFolderPath() {
        return folderPath;
    }

    public HttpHandler(String folderPath) {
        this.folderPath = folderPath;
    }

    public void handle(Request request, Response response) {
        File file = new File(folderPath + request.getPath());
        if (file.exists() && file.isFile()) {
            response.setStatusCode(OK_STATUS);
            response.setStatusText(OK_STATUS_MESSAGE);
            response.setBody(file.getPath());
        } else {
            response.setStatusCode(NOT_FOUND_STATUS);
            response.setStatusText(NOT_FOUND_STATUS_MESSAGE);
        }
        handle(response);
    }

    public UUID getUUID(Request request, Response response) {
        String uuidStr = request.getCookie(UUID_KEY);
        if (uuidStr != null) {
            return UUID.fromString(uuidStr);
        }
        UUID uuid = UUID.randomUUID();
        response.setCookie(UUID_KEY, uuid.toString());
        return uuid;
    }

    private void handle(Response response) {
        response.setProtocol(PROTOCOL);
    }
}
