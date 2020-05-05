package ru.vsu.board.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vsu.board.models.Advert;
import ru.vsu.board.profiling.Profiling;

import java.util.*;

@Repository(JdbcAdvertDao.JDBC_ADVERT_DAO_BEAN_NAME)
@Profiling
public class JdbcAdvertDao implements AdvertDao {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String TEXT = "text";
    public static final String USER_EMAIL = "email";

    public static final String SELECT_ALL_ADVERTS_QUERY = "select * from adverts";

    public static final String SELECT_ALL_ADVERTS_BY_ID_QUERY =
            "select  *" +
                    "  from  adverts" +
                    " where  id=:id;";

    public static final String SELECT_ALL_ADVERTS_BY_USER_ID_QUERY =
            "select  *" +
                    "  from  adverts" +
                    " where  user_id=:user_id;";

    public static final String SELECT_ALL_ADVERTS_BY_TEXT_QUERY =
                    "select  *" +
                    "  from  adverts" +
                    " where  (lower(name) like :text" +
                    "        or lower(description) like :text)";

    private static final String INSERT_ADVERT_QUERY =
            "insert into `adverts`(`user_id`, `name`, `description`, `price`)" +
                    "select  u.id," +
                    "        :name," +
                    "            :description," +
                    "            :price" +
                    "      from  users as u" +
                    "      where u.email=:email";

    private static final String UPDATE_ADVERT_QUERY =
            "update  adverts" +
                    "   set  name=:name," +
                    "        description=:description," +
                    "        price=:price," +
                    "        user_id=:user_id" +
                    " where  id=:id";

    public static final String DELETE_ADVERT_BY_ID_QUERY = "delete from adverts where id=:id";
    public static final String JDBC_ADVERT_DAO_BEAN_NAME = "advertDao";

    private RowMapper<Advert> advertRowMapper = (rs, rowNum) -> {
        Advert advert = new Advert();
        advert.setId(rs.getLong(ID));
        advert.setUserId(rs.getLong(USER_ID));
        advert.setName(rs.getString(NAME));
        advert.setDescription(rs.getString(DESCRIPTION));
        advert.setPrice(rs.getBigDecimal(PRICE));
        return advert;
    };

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcAdvertDao() {
    }

    @Autowired
    public JdbcAdvertDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Advert> getById(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID, id);
        List<Advert> adverts = namedParameterJdbcTemplate.query(SELECT_ALL_ADVERTS_BY_ID_QUERY, paramMap, advertRowMapper);
        if (adverts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(adverts.get(0));
    }

    @Override
    public List<Advert> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_ADVERTS_QUERY, advertRowMapper);
    }

    @Override
    public Advert insert(String userEmail, Advert advert) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(USER_EMAIL, userEmail);
        paramMap.put(NAME, advert.getName());
        paramMap.put(DESCRIPTION, advert.getDescription());
        paramMap.put(PRICE, advert.getPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_ADVERT_QUERY, new MapSqlParameterSource(paramMap), keyHolder);
        advert.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return advert;
    }

    @Override
    public void update(Advert advert) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID, advert.getId());
        paramMap.put(USER_ID, advert.getUserId());
        paramMap.put(NAME, advert.getName());
        paramMap.put(DESCRIPTION, advert.getDescription());
        paramMap.put(PRICE, advert.getPrice());
        namedParameterJdbcTemplate.update(UPDATE_ADVERT_QUERY, paramMap);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID, id);
        namedParameterJdbcTemplate.update(DELETE_ADVERT_BY_ID_QUERY, paramMap);
    }

    @Override
    public List<Advert> findByText(String text) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(TEXT, "%" + text + "%");
        return namedParameterJdbcTemplate.query(SELECT_ALL_ADVERTS_BY_TEXT_QUERY, paramMap, advertRowMapper);
    }
}
