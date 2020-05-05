package ru.vsu.board.dao.advert;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import ru.vsu.board.models.Advert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectAllAdverts extends MappingSqlQuery<Advert> {


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

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String TEXT = "text";

    public SelectAllAdverts(DataSource dataSource, String sql) {
        super(dataSource, sql);
    }

    @Override
    protected Advert mapRow(ResultSet resultSet, int i) throws SQLException {
        Advert advert = new Advert();
        advert.setId(resultSet.getLong(ID));
        advert.setUserId(resultSet.getLong(USER_ID));
        advert.setName(resultSet.getString(NAME));
        advert.setDescription(resultSet.getString(DESCRIPTION));
        advert.setPrice(resultSet.getBigDecimal(PRICE));
        return advert;
    }
}
