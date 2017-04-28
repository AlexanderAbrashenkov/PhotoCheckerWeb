package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.ReportTypeDAO;
import com.photochecker.model.ReportType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ReportTypeDAOMySqlImpl implements ReportTypeDAO {
    @Override
    public ReportType find(int id) {
        ReportType reportType = null;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `report_type`\n" +
                    "WHERE `id` = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reportType = new ReportType(id, resultSet.getString("type"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportType;
    }

    @Override
    public List<ReportType> findAll() {
        List<ReportType> reportTypeList = new ArrayList<>();
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `report_type`");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReportType reportType = new ReportType(resultSet.getInt("id"),
                        resultSet.getString("type"));
                reportTypeList.add(reportType);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportTypeList;
    }
}
