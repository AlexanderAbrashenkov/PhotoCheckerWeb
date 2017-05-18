package com.photochecker.dao.jdbc.mysql;

import com.photochecker.dao.GenericDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by market6 on 11.05.2017.
 */
public abstract class AbstractDaoMySqlImpl<T> implements GenericDao<T> {

    private static final Context CONTEXT = createContext();
    private static final DataSource DATA_SOURCE = createDataSource();

    private static Context createContext() {
        try {
            return new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DataSource createDataSource() {
        try {
            Context envContext = (Context) CONTEXT.lookup("java:/comp/env");
            return (DataSource) envContext.lookup("jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract String getSaveQuery();

    public abstract String getFindQuery();

    public abstract String getFindAllQuery();

    public abstract String getUpdateQuery();

    public abstract String getRemoveQuery();

    public abstract String getFindAllByParametersQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void prepareStatementForSave(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForRemove(PreparedStatement statement, T object) throws SQLException;

    @Override
    public int save(T t) {
        String sql = getSaveQuery();
        int generatedKey = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatementForSave(statement, t);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return generatedKey;
        }

        return generatedKey;
    }

    @Override
    public T find(int id) {
        String sql = getFindQuery();
        List<T> list;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<T> findAll() {
        String sql = getFindAllQuery();
        List<T> list;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public boolean update(T t) {
        String sql = getUpdateQuery();
        boolean succeed = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            prepareStatementForUpdate(statement, t);
            statement.execute();
            succeed = true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return succeed;
    }

    @Override
    public void remove(T t) {
        String sql = getRemoveQuery();

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            prepareStatementForRemove(statement, t);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
