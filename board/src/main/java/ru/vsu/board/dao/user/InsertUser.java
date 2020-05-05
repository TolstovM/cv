package ru.vsu.board.dao.user;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.Types;

public class InsertUser extends SqlUpdate {

    public static final String INSERT_USER_QUERY =
            "insert into users (email, username, password)" +
                    "values (:email, :username, :password)";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ID = "id";


    public InsertUser(DataSource dataSource) {
        super(dataSource, INSERT_USER_QUERY);
        super.declareParameter(new SqlParameter(EMAIL, Types.VARCHAR));
        super.declareParameter(new SqlParameter(USERNAME, Types.VARCHAR));
        super.declareParameter(new SqlParameter(PASSWORD, Types.VARCHAR));
        super.setGeneratedKeysColumnNames(ID);
        super.setReturnGeneratedKeys(true);
    }
}
