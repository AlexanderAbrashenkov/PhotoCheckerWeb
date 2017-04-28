package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.ResponsibilityDAO;
import com.photochecker.model.Distr;
import com.photochecker.model.ReportType;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by market6 on 27.04.2017.
 */
public class ResponsibilityDAOMySqlImpl implements ResponsibilityDAO {
    @Override
    public boolean create(Responsibility responsibility) {
        return false;
    }

    @Override
    public List<Responsibility> find(User user) {
        List<Responsibility> responsibilityList = new ArrayList<>();
        try {
            List<ReportType> reportTypes = DAOFactory.getDAOFactory().getReportTypeDAO().findAll();
            List<Distr> distrList = DAOFactory.getDAOFactory().getDistrDAO().findAll();

            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT res.`report_type`, res.`distr_id`\n" +
                    "FROM `responsibility_db` res\n" +
                    "WHERE\n" +
                    "res.`resp_user` = " + user.getId());

            ResultSet resultSet = statement.executeQuery();

            //Set<Integer> regionAllowed = new HashSet<>();
            while (resultSet.next()) {
                int repType = resultSet.getInt("report_type");
                int distrId = resultSet.getInt("distr_id");

                Responsibility responsibility = new Responsibility(
                        reportTypes.stream().filter(reportType -> reportType.getId() == repType).findFirst().get(),
                        distrList.stream().filter(distr -> distr.getId() == distrId).findFirst().get(),
                        user
                );
                responsibilityList.add(responsibility);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsibilityList;
    }

    @Override
    public List<Responsibility> findAll() {
        return null;
    }

    @Override
    public boolean update(Responsibility responsibility) {
        return false;
    }
}
