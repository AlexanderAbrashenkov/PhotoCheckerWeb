package com.photochecker.mysqlDAO;

import com.photochecker.dao.GenericDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by market6 on 11.05.2017.
 */
public abstract class AbstractDAOMySqlImpl<T> implements GenericDAO<T> {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract String getCreateQuery();

    public abstract String getFindQuery();

    public abstract String getFindAllQuery();

    public abstract String getUpdateQuery();

    public abstract String getFindAllByParametersQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void prepareStatementForCreate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    @Override
    public boolean create(T t) {
        String sql = getCreateQuery();
        boolean succeed = false;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForCreate(statement, t);
            statement.execute();
            succeed = true;
        } catch (SQLException e) {
            return false;
        } finally {
            closeConnection();
        }

        return succeed;
    }

    @Override
    public T find(int id) {
        String sql = getFindQuery();
        List<T> list;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            return null;
        } finally {
            closeConnection();
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

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            return null;
        } finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public boolean update(T t) {
        String sql = getUpdateQuery();
        boolean succeed = false;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, t);
            statement.execute();
            succeed = true;
        } catch (SQLException e) {
            return false;
        } finally {
            closeConnection();
        }
        return succeed;
    }
}
