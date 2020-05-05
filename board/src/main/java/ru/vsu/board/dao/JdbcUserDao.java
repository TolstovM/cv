package ru.vsu.board.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vsu.board.models.Role;
import ru.vsu.board.models.User;
import ru.vsu.board.profiling.Profiling;

import java.util.*;

@Repository(JdbcUserDao.JDBC_USER_DAO_BEAN_NAME)
@Profiling
public class JdbcUserDao implements UserDao {

    public static final int FIRST_ITEM_INDEX = 0;
    public static final String JDBC_USER_DAO_BEAN_NAME = "userDao";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ID = "id";
    public static final String USER_ID = "id";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";

    public static final String SELECT_ALL_USERS_QUERY = "select * from users";
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
    public static final String INSERT_DEFAULT_ROLE_QUERY =
            "insert into users_roles (user_id, role_id)" +
                    "values (:id, 1)";
    public static final String INSERT_USER_QUERY =
            "insert into users (email, username, password)" +
                    "values (:email, :username, :password)";
    public static final String UPDATE_USER_QUERY =
            "update  users" +
                    "   set  email=:email," +
                    "        username=:username," +
                    "        password=:password" +
                    " where  id=:id";

    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setEmail(rs.getString(EMAIL));
        user.setUsername(rs.getString(USERNAME));
        user.setPassword(rs.getString(PASSWORD));
        return user;
    };

    private ResultSetExtractor<List<User>> usersResultSetExtractor = rs -> {
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
    };

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUserDao() {
    }

    @Autowired
    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> getByIdWithRoles(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID, id);
        return findWithRoles(SELECT_USER_BY_ID_WITH_ROLES, paramMap);
    }

    @Override
    public Optional<User> getByEmailWithRoles(String email) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(EMAIL, email);
        return findWithRoles(SELECT_USER_BY_EMAIL_WITH_ROLES, paramMap);
    }

    @Override
    public List<User> getAll() {
        return  namedParameterJdbcTemplate.query(SELECT_ALL_USERS_QUERY, userRowMapper);
    }

    @Override
    public User insert(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(EMAIL, user.getEmail());
        paramMap.put(USERNAME, user.getUsername());
        paramMap.put(PASSWORD, user.getPassword());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_USER_QUERY, new MapSqlParameterSource(paramMap), keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        insertDefaultRole(user);
        return user;
    }

    @Override
    public void update(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(EMAIL, user.getEmail());
        paramMap.put(USERNAME, user.getUsername());
        paramMap.put(PASSWORD, user.getUsername());
        paramMap.put(ID, user.getId());
        namedParameterJdbcTemplate.update(UPDATE_USER_QUERY, paramMap);
    }

    private void insertDefaultRole(User user) {
        Map<String, Object> roleParamMap = new HashMap<>();
        roleParamMap.put(USER_ID, user.getId());
        namedParameterJdbcTemplate.update(INSERT_DEFAULT_ROLE_QUERY, roleParamMap);
    }

    private Optional<User> findWithRoles(String sql, Map<String, Object> paramMap) {
        List<User> users = namedParameterJdbcTemplate.query(sql, paramMap, usersResultSetExtractor);
        assert users != null;
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(FIRST_ITEM_INDEX));
    }
}
