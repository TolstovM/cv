package ru.vsu.persistence.conncection;

import lombok.SneakyThrows;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MyDataSource {

    public static final String NAME = "java:/comp/env/jdbc/MyLocalDB";

    protected MyDataSource() {}

    private static DataSource instance;

    @SneakyThrows
    public static DataSource getInstance() {
        if (instance == null) {
            Context context = new InitialContext();
            instance = (DataSource) context.lookup(NAME);
        }
        return instance;
    }
}
