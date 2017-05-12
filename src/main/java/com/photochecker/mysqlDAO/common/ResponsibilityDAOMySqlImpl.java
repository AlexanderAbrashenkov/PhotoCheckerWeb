package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.common.ResponsibilityDAO;
import com.photochecker.model.*;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ResponsibilityDAOMySqlImpl extends AbstractDAOMySqlImpl<Responsibility> implements ResponsibilityDAO {

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return null;
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `responsibility_db`";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `responsibility_db`\n" +
                "SET `resp_user` = ?\n" +
                "WHERE `report_type` = ?\n" +
                "AND `distr_id` = ?";
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "SELECT DISTINCT res.`report_type`, res.`distr_id`, res.`resp_user`\n" +
                "FROM `responsibility_db` res\n" +
                "WHERE\n" +
                "res.`resp_user` = ?";
    }

    @Override
    protected List<Responsibility> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<Responsibility> responsibilityList = new ArrayList<>();

        List<ReportType> reportTypes = DAOFactory.getDAOFactory().getReportTypeDAO().findAll();
        List<Distr> distrList = DAOFactory.getDAOFactory().getDistrDAO().findAll();

        while (resultSet.next()) {
            int repType = resultSet.getInt("report_type");
            int distrId = resultSet.getInt("distr_id");

            Responsibility responsibility = new Responsibility(
                    reportTypes.stream().filter(reportType -> reportType.getId() == repType).findFirst().get(),
                    distrList.stream().filter(distr -> distr.getId() == distrId).findFirst().get(),
                    DAOFactory.getDAOFactory().getUserDAO().find(resultSet.getInt("resp_user"))
            );
            responsibilityList.add(responsibility);
        }
        return responsibilityList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Responsibility object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Responsibility object) throws SQLException {
        statement.setInt(1, object.getUser().getId());
        statement.setInt(2, object.getReportType().getId());
        statement.setInt(3, object.getDistr().getId());
    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {
        User user = (User) params[0];

        statement.setInt(1, user.getId());
    }
}
