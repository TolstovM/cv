package ru.vsu.board.dao.user;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class UpdateUser extends SqlUpdate {

    public static final String UPDATE_USER_QUERY =
                    "update  users" +
                    "   set  email=:email," +
                    "        username=:username," +
                    "        password=:password" +
                    " where  id=:id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ID = "id";

    public UpdateUser(DataSource dataSource) {
        super(dataSource, UPDATE_USER_QUERY);
        super.declareParameter(new SqlParameter(EMAIL, Types.VARCHAR));
        super.declareParameter(new SqlParameter(USERNAME, Types.VARCHAR));
        super.declareParameter(new SqlParameter(PASSWORD, Types.VARCHAR));
        super.declareParameter(new SqlParameter(ID, Types.BIGINT));
    }
}
