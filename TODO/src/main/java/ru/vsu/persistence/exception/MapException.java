package ru.vsu.persistence.exception;

public class MapException extends RuntimeException {

    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public MapException(String message, String fieldName, Throwable throwable) {
        super(message, throwable);
        this.fieldName = fieldName;
    }

    public MapException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public MapException(String message) {
        super(message);
    }
}
