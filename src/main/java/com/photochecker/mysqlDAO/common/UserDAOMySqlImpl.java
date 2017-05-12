package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.common.UserDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import com.photochecker.model.WrongUserException;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class UserDAOMySqlImpl extends AbstractDAOMySqlImpl<User> implements UserDAO {


    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `users`\n" +
                "WHERE `id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `users`";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "SELECT * FROM `users` " +
                "WHERE `user_login` = ? ";
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<User> userList = new ArrayList<>();

        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String login = resultSet.getString("user_login");
            String userName = resultSet.getString("user_name");

            int userRole = resultSet.getInt("user_role");

            User user = new User(id, login, userName, userRole, null);

            List<ReportType> reportTypeList = DAOFactory.getDAOFactory().getReportTypeDAO().findAllByParameters(user);

            user.setReportTypeList(reportTypeList);

            userList.add(user);
        }
        return userList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, User object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) {

    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {
        String login = (String) params[0];
        statement.setString(1, login);
    }
}
