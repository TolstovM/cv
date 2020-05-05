package ru.vsu.board.dao.advert;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class DeleteAdvertById extends SqlUpdate {

    private static final String DELETE_ADVERT_BY_ID_QUERY = "delete from adverts where id=:id";
    public static final String ID = "id";

    public DeleteAdvertById(DataSource dataSource) {
        super(dataSource, DELETE_ADVERT_BY_ID_QUERY);
        super.declareParameter(new SqlParameter(ID, Types.BIGINT));
    }
}
