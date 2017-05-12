package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.common.ReportTypeDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ReportTypeDAOMySqlImpl extends AbstractDAOMySqlImpl<ReportType> implements ReportTypeDAO {


    @Override
    public String getCreateQuery() {
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
    public String getFindAllByParametersQuery() {
        return "SELECT r.`id`, r.`type`\n" +
                "FROM `report_type` r\n" +
                "INNER JOIN `report_type_user` ru ON ru.`report_type` = r.`id`\n" +
                "where ru.`user_id` = ?";
    }

    @Override
    protected List<ReportType> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<ReportType> reportTypeList = new ArrayList<>();
        while (resultSet.next()) {
            ReportType reportType = new ReportType(resultSet.getInt("id"),
                    resultSet.getString("type"));
            reportTypeList.add(reportType);
        }
        return reportTypeList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, ReportType object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ReportType object) {

    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {
        User user = (User) params[0];

        statement.setInt(1, user.getId());
    }
}
