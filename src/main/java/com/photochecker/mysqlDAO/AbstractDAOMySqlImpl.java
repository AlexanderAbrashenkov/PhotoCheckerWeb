package com.photochecker.mysqlDAO;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;

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

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException, PersistException;

    protected abstract void prepareStatementForCreate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException;

    @Override
    public boolean create(T t) throws PersistException {
        String sql = getCreateQuery();
        boolean succeed = false;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForCreate(statement, t);
            statement.execute();
            succeed = true;
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection();
        }

        return succeed;
    }

    @Override
    public T find(int id) throws PersistException {
        String sql = getFindQuery();
        List<T> list;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection();
        }

        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<T> findAll() throws PersistException {
        String sql = getFindAllQuery();
        List<T> list;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection();
        }
        return list;
    }

    @Override
    public boolean update(T t) throws PersistException {
        String sql = getUpdateQuery();
        boolean succeed = false;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, t);
            statement.execute();
            succeed = true;
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection();
        }
        return succeed;
    }

    @Override
    public List<T> findAllByParameters(Object... params) throws PersistException {
        String sql = getFindAllByParametersQuery();
        List<T> list;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForFindAllByParameters(statement, params);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection();
        }

        return list;
    }
}
