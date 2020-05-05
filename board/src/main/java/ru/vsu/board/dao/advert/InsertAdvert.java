package ru.vsu.board.dao.advert;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertAdvert extends SqlUpdate {
    private static final String INSERT_ADVERT_QUERY =
            "insert into `adverts`(`user_id`, `name`, `description`, `price`)" +
                    "select  u.id," +
                    "        :name," +
                    "            :description," +
                    "            :price" +
                    "      from  users as u" +
                    "      where u.email=:email";
    public static final String USER_EMAIL = "email";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String ID = "id";

    public InsertAdvert(DataSource dataSource) {
        super(dataSource, INSERT_ADVERT_QUERY);
        super.declareParameter(new SqlParameter(USER_EMAIL, Types.VARCHAR));
        super.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        super.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        super.declareParameter(new SqlParameter(PRICE, Types.DECIMAL));
        super.setGeneratedKeysColumnNames(ID);
        super.setReturnGeneratedKeys(true);
    }
}
