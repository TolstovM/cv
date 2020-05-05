package ru.vsu.board.dao.user;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertDefaultRoleToUser extends SqlUpdate {
    public static final String INSERT_DEFAULT_ROLE_QUERY =
            "insert into users_roles (user_id, role_id)" +
                    "values (:id, 1)";
    public static final String USER_ID = "id";

    public InsertDefaultRoleToUser(DataSource dataSource) {
        super(dataSource, INSERT_DEFAULT_ROLE_QUERY);
        super.declareParameter(new SqlParameter(USER_ID, Types.BIGINT));
    }
}
