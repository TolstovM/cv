package ru.vsu.board.dao.user;


import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import ru.vsu.board.models.Role;
import ru.vsu.board.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectUserWithRoles {

    public static final String SELECT_USER_BY_ID_WITH_ROLES =
                    "select  u.id," +
                    "        u.username," +
                    "        u.email," +
                    "        u.password," +
                    "        r.id as role_id," +
                    "        r.name as role_name" +
                    "  from  users u" +
                    "        left join users_roles ur on" +
                    "          u.id = ur.user_id" +
                    "        left join roles r on" +
                    "          ur.role_id = r.id" +
                    "  where u.id=:id;";

    public static final String SELECT_USER_BY_EMAIL_WITH_ROLES =
                    "select  u.id," +
                    "        u.username," +
                    "        u.email," +
                    "        u.password," +
                    "        r.id as role_id," +
                    "        r.name as role_name" +
                    "  from  users u" +
                    "        left join users_roles ur on" +
                    "          u.id = ur.user_id" +
                    "        left join roles r on" +
                    "          ur.role_id = r.id" +
                    "  where u.email=:email;";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";


    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SelectUserWithRoles(DataSource ds, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dataSource = ds;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<User> execute(String query, Map<String, Object> params) {
        return this.namedParameterJdbcTemplate.query(query, params, rs -> {
            Map<Long, User> userMap = new HashMap<>();
            Map<Long, Role> roleMap = new HashMap<>();
            User user;
            Role role;
            while (rs.next()) {
                Long id = rs.getLong(ID);
                user = userMap.get(id);
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setEmail(rs.getString(EMAIL));
                    user.setUsername(rs.getString(USERNAME));
                    user.setPassword(rs.getString(PASSWORD));
                    userMap.put(id, user);
                }
                Long roleId = rs.getLong(ROLE_ID);
                role = roleMap.get(roleId);
                if (role == null) {
                    role = new Role();
                    role.setId(roleId);
                    role.setName(rs.getString(ROLE_NAME));
                    roleMap.put(roleId, role);
                }
                user.addRole(role);
            }
            return new ArrayList<>(userMap.values());
        });
    }
}
