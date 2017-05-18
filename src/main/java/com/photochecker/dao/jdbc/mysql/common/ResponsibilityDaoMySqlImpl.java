package com.photochecker.dao.jdbc.mysql.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Distr;
import com.photochecker.model.ReportType;
import com.photochecker.model.Responsibility;
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
public class ResponsibilityDaoMySqlImpl extends AbstractDaoMySqlImpl<Responsibility> implements ResponsibilityDao {

    @Override
    public String getSaveQuery() {
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
    public String getRemoveQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "SELECT DISTINCT res.`report_type`, res.`distr_id`, res.`resp_user`\n" +
                "FROM `responsibility_db` res\n" +
                "WHERE\n" +
                "res.`resp_user` = ?";
    }

    @Override
    protected List<Responsibility> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Responsibility> responsibilityList = new ArrayList<>();

        List<ReportType> reportTypes = DaoFactory.getDAOFactory().getReportTypeDAO().findAll();
        List<Distr> distrList = DaoFactory.getDAOFactory().getDistrDAO().findAll();

        while (resultSet.next()) {
            int repType = resultSet.getInt("report_type");
            int distrId = resultSet.getInt("distr_id");

            Responsibility responsibility = new Responsibility(
                    reportTypes.stream().filter(reportType -> reportType.getId() == repType).findFirst().get(),
                    distrList.stream().filter(distr -> distr.getId() == distrId).findFirst().get(),
                    DaoFactory.getDAOFactory().getUserDAO().find(resultSet.getInt("resp_user"))
            );
            responsibilityList.add(responsibility);
        }
        return responsibilityList;
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Responsibility object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Responsibility object) throws SQLException {
        statement.setInt(1, object.getUser().getId());
        statement.setInt(2, object.getReportType().getId());
        statement.setInt(3, object.getDistr().getId());
    }

    @Override
    protected void prepareStatementForRemove(PreparedStatement statement, Responsibility object) throws SQLException {

    }

    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setInt(1, user.getId());
    }

    @Override
    public List<Responsibility> findAllByUser(User user) {
        String sql = getFindAllByParametersQuery();
        List<Responsibility> list;

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
