package ru.vsu.io;

import java.util.Scanner;

public class PropertiesLoaderFromConsole implements PropertiesLoader{
    public static final String GET_PROPERTY_MESSAGE = "Enter value for %s:";
    private Scanner in = new Scanner(System.in);

    @Override
    public String getProperty(String key) throws NoSuchFieldException {
        System.out.println(String.format(GET_PROPERTY_MESSAGE, key));
        return in.nextLine();
    }
}
