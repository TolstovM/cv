package ru.vsu.persistence.repository;

import ru.vsu.persistence.conncection.MyDataSource;
import ru.vsu.persistence.util.Criteria;
import ru.vsu.persistence.util.DataMap;
import ru.vsu.persistence.util.DataMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Repository<T> {

    public static final int SQL_INDEX_STARTS_WITH = 1;
    private DataMap<T> dataMap;
    private DataMapper<T> dataMapper;

    public Repository(Class<T> clazz) {
        this.dataMap = new DataMap<>(clazz);
        this.dataMapper = new DataMapper<>(this.dataMap, clazz);
    }

    public Optional<T> getById(Long id) throws SQLException {
        try (Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement stmt = connection.prepareStatement(this.dataMap.getSelectByIdQuery())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            stmt.setObject(SQL_INDEX_STARTS_WITH, id);
            ResultSet rs = stmt.executeQuery();
            return this.dataMapper.mapAll(rs).stream().findFirst();
        }
    }

    public List<T> getAll() throws SQLException {
        try (Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement stmt = connection.prepareStatement(this.dataMap.getSelectAllQuery())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ResultSet resultSet = stmt.executeQuery();
            return this.dataMapper.mapAll(resultSet);
        }
    }

    public List<T> findByCriteria(Criteria criteria) throws SQLException {
        try (Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement stmt = connection.prepareStatement(this.dataMap.getFindQuery(criteria))) {
            stmt.setObject(SQL_INDEX_STARTS_WITH, criteria.getValue());
            ResultSet resultSet = stmt.executeQuery();
            return this.dataMapper.mapAll(resultSet);
        }
    }

    public boolean create(T item) throws SQLException {
        try(Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(this.dataMap.getInsertQuery())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            for (int i = 0; i < this.dataMap.getColumnMaps().size(); i++) {
                statement.setObject(i + SQL_INDEX_STARTS_WITH, this.dataMap.getColumnMaps().get(i).getValue(item));
            }
            return statement.execute();
        }
    }

    public boolean deleteById(Long id) throws SQLException {
        try(Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(this.dataMap.getDeleteQuery())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            statement.setLong(SQL_INDEX_STARTS_WITH, id);
            return statement.execute();
        }
    }

    public boolean update(T item) throws SQLException {
        try (Connection connection = MyDataSource.getInstance().getConnection(); PreparedStatement stmt = connection.prepareStatement(this.dataMap.getUpdateQuery())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            for (int i = 0; i < this.dataMap.getColumnMaps().size(); i++) {
                stmt.setObject(i + SQL_INDEX_STARTS_WITH, this.dataMap.getColumnMaps().get(i).getValue(item));
            }
            Object id = this.dataMap.getIdColumn().getValue(item);
            stmt.setObject(this.dataMap.getColumnMaps().size() + SQL_INDEX_STARTS_WITH, id);
            return stmt.execute();
        }
    }
}
