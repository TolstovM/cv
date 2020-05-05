package ru.vsu.http.entities;


import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Response {

    public static final int LENGTH_OF_SEPARATOR = 2;
    private String protocol;
    private int statusCode;
    private String statusText;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> cookieMap = new HashMap<>();
    private String body;

    private static String FIRST_LINE = "%s %s %s\r\n";
    private static String LINE = "%s: %s\r\n";
    private static String SET_COOKIE_HEADER = "Set-cookie";
    private static String SET_COOKIE_VALUE = "%s=%s; ";

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Response() {
    }

    public Response(String protocol, int statusCode, String statusText) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public byte[] headersToBytes() {
        return headerToString().getBytes();
    }

    public void setCookie(String key, String value) {
        this.cookieMap.put(key, value);
    }

    private String headerToString() {
        StringBuilder text = new StringBuilder(String.format(FIRST_LINE, this.protocol, this.statusCode, this.statusText));
        this.headers.forEach((key, value) -> text.append(String.format(LINE, key, value)));
        Optional<String> setCookiesString = this.cookiesToString();
        setCookiesString.ifPresent(s -> text.append(String.format(LINE, SET_COOKIE_HEADER, s)));
        if(body != null) {
            text.append("\r\n");
        }
        return text.toString();
    }

    private Optional<String> cookiesToString() {
        if (cookieMap.entrySet().size() == 0) {
            return Optional.empty();
        }

        StringBuilder text = new StringBuilder();
        this.cookieMap.forEach((key, value) -> text.append(String.format(SET_COOKIE_VALUE, key, value)));
        text.setLength(text.length());
        return Optional.of(text.toString());
    }
}
