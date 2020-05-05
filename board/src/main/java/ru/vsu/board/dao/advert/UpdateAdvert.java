package ru.vsu.board.dao.advert;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class UpdateAdvert extends SqlUpdate {

    private static final String UPDATE_ADVERT_QUERY =
                    "update  adverts" +
                    "   set  name=:name," +
                    "        description=:description," +
                    "        price=:price," +
                    "        user_id=:user_id" +
                    " where  id=:id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String USER_ID = "user_id";
    public static final String ID = "id";

    public UpdateAdvert(DataSource dataSource) {
        super(dataSource, UPDATE_ADVERT_QUERY);
        super.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        super.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        super.declareParameter(new SqlParameter(PRICE, Types.DECIMAL));
        super.declareParameter(new SqlParameter(USER_ID, Types.BIGINT));
        super.declareParameter(new SqlParameter(ID, Types.BIGINT));
    }
}
