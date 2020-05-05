package ru.vsu.http.entities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private String protocol;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    public String getCookie(String key) {
        return this.cookies.get(key);
    }

    public Request(String method, String path, String protocol, InputStream inputStream, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
    }
}
