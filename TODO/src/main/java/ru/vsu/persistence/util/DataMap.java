package ru.vsu.persistence.util;

import ru.vsu.persistence.annotation.Column;
import ru.vsu.persistence.annotation.Id;
import ru.vsu.persistence.annotation.Table;
import ru.vsu.persistence.exception.MapException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataMap<T> {

    public static final String ID_COLUMN_DOES_NOT_FOUND_EXCEPTION_MESSAGE = "Id column does not found";
    public static final String SELECT_ALL_QUERY = "select %s from %s;";
    public static final String SELECT_BY_ID_QUERY = "select %s from %s where %s=?;";
    public static final String INSERT_QUERY = "insert into %s (%s) values(%s)";
    public static final String UPDATE_QUERY = "update %s set %s where %s=?;";
    public static final String DELETE_BY_ID_QUERY = "delete from %s where %s=?;";
    public static final String PLACEHOLDER = "?";
    public static final String EQUALS_PLACEHOLDER = "=?";
    public static final String REGEX = ", ";
    public static final String SUCH_FIELD_DOES_NOT_FOUND_EXCEPTION_MESSAGE = "Cannot find field in dataMap";
    public static final String SELECT_BY_CRITERIA_QUERY = "select %s from %s where %s";

    private Class<T> clazz;
    private String columns;
    private String tableName;
    private ColumnMap idColumn;
    private List<ColumnMap> columnMaps;

    private String selectAllQuery;
    private String selectByIdQuery;
    private String selectByCriteriaQuery;
    private String insertQuery;
    private String updateQuery;
    private String deleteQuery;

    public DataMap(Class<T> clazz) {
        this.clazz = clazz;
        init();
    }

    public String getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }

    public ColumnMap getIdColumn() {
        return idColumn;
    }

    public String getSelectAllQuery() {
        if (selectAllQuery == null) {
            initSelectAllQuery();
        }
        return selectAllQuery;
    }

    public String getSelectByIdQuery() {
        if (selectByIdQuery==null) {
            initSelectByIdQuery();
        }
        return selectByIdQuery;
    }

    public String getFindQuery(Criteria criteria) {
        String stringCriteria = getColumnName(criteria.getField()) + criteria.getSqlOperator() + PLACEHOLDER;
        return String.format(SELECT_BY_CRITERIA_QUERY, this.columns, this.tableName, stringCriteria);
    }

    public String getInsertQuery() {
        if (insertQuery==null) {
            initInsertQuery();
        }
        return insertQuery;
    }

    public String getUpdateQuery() {
        if(updateQuery==null) {
            initUpdateQuery();
        }
        return updateQuery;
    }

    public String getDeleteQuery() {
        if(deleteQuery==null) {
            initDeleteQuery();
        }
        return deleteQuery;
    }

    public List<ColumnMap> getColumnMaps() {
        return columnMaps;
    }

    public String getColumnName(String field) {
        ColumnMap column = this.columnMaps.stream()
                .filter(columnMap -> columnMap.getFieldName().equals(field))
                .findFirst()
                .orElseThrow(() -> new MapException(SUCH_FIELD_DOES_NOT_FOUND_EXCEPTION_MESSAGE, field));
        return column.getColumnName();
    }

    private void init() {
        this.columnMaps = initColumnMaps();
        this.columns = initColumns();
        this.tableName = initTableName();
        this.idColumn = initIdColumn().orElseThrow(() -> new  MapException(ID_COLUMN_DOES_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private List<ColumnMap> initColumnMaps() {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Column.class) != null)
                .map(ColumnMap::new)
                .collect(Collectors.toList());
    }

    private String initColumns() {
        StringBuilder columns = new StringBuilder();
        this.columnMaps.forEach(columnMap -> columns.append(columnMap.getColumnName()).append(REGEX));
        columns.delete(columns.length() - REGEX.length(), columns.length());
        return columns.toString();
    }

    private String initTableName() {
        Table classAnnotation = this.clazz.getAnnotation(Table.class);
        return classAnnotation != null ? classAnnotation.name() : clazz.getName().toLowerCase();
    }

    private Optional<ColumnMap> initIdColumn() {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Id.class) != null)
                .map(ColumnMap::new)
                .findFirst();
    }

    private void initSelectAllQuery() {
        this.selectAllQuery = String.format(SELECT_ALL_QUERY, this.columns, this.tableName);
    }

    private void initSelectByIdQuery() {
        this.selectByIdQuery = String.format(SELECT_BY_ID_QUERY, this.columns, this.tableName, this.idColumn.getColumnName());
    }

    private void initInsertQuery() {
        StringBuilder values = new StringBuilder();
        this.columnMaps.forEach(columnMap ->values.append(PLACEHOLDER).append(REGEX));
        values.delete(values.length() - REGEX.length(), values.length());

        this.insertQuery = String.format(INSERT_QUERY, this.tableName, this.columns, values.toString());
    }

    private void initUpdateQuery() {
        StringBuilder values = new StringBuilder();
        this.columnMaps.forEach(columnMap -> values.append(columnMap.getColumnName()).append(EQUALS_PLACEHOLDER).append(REGEX));
        values.delete(values.length() - REGEX.length(), values.length());

        this.updateQuery = String.format(UPDATE_QUERY, this.tableName, values.toString(), this.idColumn.getColumnName());
    }


    private void initDeleteQuery() {
        this.deleteQuery = String.format(DELETE_BY_ID_QUERY, this.tableName, this.idColumn.getColumnName());
    }
}
