package ru.vsu.http.exceptions;

public class WrongHttpRequest extends Exception {
    public WrongHttpRequest(String message) {
        super(message);
    }
}
