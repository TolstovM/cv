package ru.vsu.board.dao.user;

import org.springframework.jdbc.object.MappingSqlQuery;
import ru.vsu.board.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAllUsers extends MappingSqlQuery<User> {

    public static final String SELECT_ALL_USERS_QUERY = "select * from users";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public SelectAllUsers(DataSource ds) {
        super(ds, SELECT_ALL_USERS_QUERY);
    }

    @Override
    protected User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(ID));
        user.setEmail(resultSet.getString(EMAIL));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));
        return user;
    }
}
