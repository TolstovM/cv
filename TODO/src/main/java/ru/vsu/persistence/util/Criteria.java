package ru.vsu.persistence.util;

public class Criteria {

    public static final String EQUAL = "=";

    private String sqlOperator;
    private String field;
    private Object value;

    public String getSqlOperator() {
        return sqlOperator;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public Criteria(String sqlOperator, String field, Object value) {
        this.sqlOperator = sqlOperator;
        this.field = field;
        this.value = value;
    }

    public String getSqlCriteria(DataMap<?> map) {
        return map.getColumnName(this.field) + this.sqlOperator + DataMap.PLACEHOLDER;
    }
}
