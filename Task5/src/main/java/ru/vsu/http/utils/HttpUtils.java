package ru.vsu.http.utils;

import ru.vsu.http.entities.Request;

import java.util.*;

public final class HttpUtils {

    private static final int ITEM_IN_HEADER_LINE = 2;
    private static final String HEADER_REGEX = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private static final String COOKIE_KEY = "Cookie";
    private static final String COOKIE_REGEX = "; ";
    private static final String COOKIE_INNER_REGEX = "=";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;

    public static final String REQUEST_HEADERS_END_LINE = "\r\n";

    private HttpUtils() {}

    public static Map<String,String> getHeaders(List<String> metadata) {
        Map<String, String> headers = new HashMap<>();
        metadata.forEach(line -> {
            String[] tmp = line.split(HEADER_REGEX, ITEM_IN_HEADER_LINE);
            headers.put(tmp[KEY_INDEX].trim(), tmp[VALUE_INDEX].trim());
        });
        return headers;
    }

    public static void initCookiesInRequest(Request request) {
        String cookiesLine = request.getHeader(COOKIE_KEY);
        if (cookiesLine == null) {
            return;
        }
        Map<String, String> cookies = new HashMap<>();
        Arrays.stream(cookiesLine.split(COOKIE_REGEX))
                .forEach(cookie -> {
                    String[] tmp = cookie.split(COOKIE_INNER_REGEX);
                    cookies.put(tmp[COOKIE_KEY_INDEX].trim(), tmp[COOKIE_VALUE_INDEX].trim());
                });
        request.setCookies(cookies);
    }
}
