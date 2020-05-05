package ru.vsu.persistence.util;

import ru.vsu.persistence.exception.MapException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataMapper<T> {

    private DataMap<T> dataMap;
    private Class<T> clazz;

    public DataMapper(DataMap<T> dataMap, Class<T> clazz) {
        this.dataMap = dataMap;
        this.clazz = clazz;
    }

    public List<T> mapAll(ResultSet resultSet) throws SQLException {
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(map(resultSet));
        }
        return result;
    }

    private T map(ResultSet resultSet) throws SQLException {
        try {
            T item = this.clazz.newInstance();
            for (ColumnMap column : this.dataMap.getColumnMaps()) {
                Object value = resultSet.getObject(column.getColumnName());
                column.setField(item, value);
            }
            return item;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new MapException("Exception during mapping", "", e);
        }
    }
}
