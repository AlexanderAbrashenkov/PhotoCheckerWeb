package com.photochecker.dao.jdbc.mysql.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import com.photochecker.dao.jdbc.mysql.AbstractDaoMySqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ReportTypeDaoMySqlImpl extends AbstractDaoMySqlImpl<ReportType> implements ReportTypeDao {


    @Override
    public String getSaveQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `report_type`\n" +
                "WHERE `id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `report_type`";
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
        return "SELECT r.`id`, r.`type`\n" +
                "FROM `report_type` r\n" +
                "INNER JOIN `report_type_user` ru ON ru.`report_type` = r.`id`\n" +
                "where ru.`user_id` = ?";
    }

    @Override
    protected List<ReportType> parseResultSet(ResultSet resultSet) throws SQLException {
        List<ReportType> reportTypeList = new ArrayList<>();
        while (resultSet.next()) {
            ReportType reportType = new ReportType(resultSet.getInt("id"),
                    resultSet.getString("type"));
            reportTypeList.add(reportType);
        }
        return reportTypeList;
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, ReportType object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ReportType object) {

    }

    @Override
    protected void prepareStatementForRemove(PreparedStatement statement, ReportType object) throws SQLException {

    }

    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setInt(1, user.getId());
    }

    @Override
    public List<ReportType> findAllByUser(User user) {
        String sql = getFindAllByParametersQuery();
        List<ReportType> list;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            prepareStatementForFindAllByParameters(statement, user);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }
}