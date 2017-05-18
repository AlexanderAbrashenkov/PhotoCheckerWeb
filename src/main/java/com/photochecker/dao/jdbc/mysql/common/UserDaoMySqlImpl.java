package com.photochecker.dao.jdbc.mysql.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import com.photochecker.dao.jdbc.mysql.AbstractDaoMySqlImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class UserDaoMySqlImpl extends AbstractDaoMySqlImpl<User> implements UserDao {


    @Override
    public String getSaveQuery() {
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
    public String getRemoveQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "SELECT * FROM `users` " +
                "WHERE `user_login` = ? ";
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String login = resultSet.getString("user_login");
            String userName = resultSet.getString("user_name");

            int userRole = resultSet.getInt("user_role");

            User user = new User(id, login, userName, userRole, null);

            List<ReportType> reportTypeList = DaoFactory.getDAOFactory().getReportTypeDAO().findAllByUser(user);

            user.setReportTypeList(reportTypeList);

            userList.add(user);
        }
        return userList;
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, User object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) {

    }

    @Override
    protected void prepareStatementForRemove(PreparedStatement statement, User object) throws SQLException {

    }

    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, String login) throws SQLException {
        statement.setString(1, login);
    }

    @Override
    public List<User> findAllByLogin(String login) {
        String sql = getFindAllByParametersQuery();
        List<User> list;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            prepareStatementForFindAllByParameters(statement, login);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public User checkLoginAndPassword(String login, String password) {

        User user = null;

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` " +
                    "WHERE `user_login` = ? ")) {

            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            String userName = resultSet.getString("user_name");
            String userPass = resultSet.getString("user_pass");
            String userSalt = resultSet.getString("salt");

            String passSalt = password + userSalt;
            StringBuffer code = new StringBuffer();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                byte bytes[] = passSalt.getBytes();
                byte digest[] = messageDigest.digest(bytes); //save code
                for (int i = 0; i < digest.length; ++i) {
                    code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if (!code.toString().equals(userPass)) {
                return null;
            }

            int userRole = resultSet.getInt("user_role");

            user = new User(id, login, userName, userRole, null);

            List<ReportType> reportTypeList = DaoFactory.getDAOFactory().getReportTypeDAO().findAllByUser(user);

            user.setReportTypeList(reportTypeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
