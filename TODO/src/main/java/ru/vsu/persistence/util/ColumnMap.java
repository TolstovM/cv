package ru.vsu.persistence.util;

import ru.vsu.persistence.annotation.Column;
import ru.vsu.persistence.exception.MapException;

import java.lang.reflect.Field;

public class ColumnMap {

    public static final String FIELD_DOES_NOT_HAVE_ANNOTATION_COLUMN_EXCEPTION_MESSAGE = "Field does not have annotation @Column";
    public static final String CANNOT_SET_FIELD_S_VALUE_EXCEPTION_MESSAGE = "Cannot set field's value";
    private Field field;

    ColumnMap(Field field) {
        if(field.getAnnotation(Column.class) == null) {
            throw new MapException(FIELD_DOES_NOT_HAVE_ANNOTATION_COLUMN_EXCEPTION_MESSAGE, field.getName());
        }
        this.field = field;
        this.field.setAccessible(true);
    }

    public String getFieldName() {
        return this.field.getName();
    }

    public String getColumnName() {
        return this.field.getAnnotation(Column.class).name();
    }

    public Object getValue(Object object) {
        try {
            return this.field.get(object);
        } catch (IllegalAccessException e) {
            throw new MapException(e.getMessage(), this.field.getName(), e);
        }
    }

    public void setField(Object object, Object value) {
        try {
            this.field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new MapException(CANNOT_SET_FIELD_S_VALUE_EXCEPTION_MESSAGE, this.field.getName(), e);
        }
    }
}
