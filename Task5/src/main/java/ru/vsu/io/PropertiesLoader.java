package ru.vsu.io;

public interface PropertiesLoader {
    String getProperty(String key) throws NoSuchFieldException;
}
