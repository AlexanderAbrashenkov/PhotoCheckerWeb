package com.photochecker.dao.jdbc.mysql.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.DistrDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Region;
import com.photochecker.dao.jdbc.mysql.AbstractDaoMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class DistrDaoMySqlImpl extends AbstractDaoMySqlImpl<Distr> implements DistrDao {

    @Override
    public String getSaveQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `distr_db`\n" +
                "WHERE `distr_id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `distr_db`";
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
        return "select distinct d.`distr_name`, d.`distr_id`, d.`region_id` \n" +
                "from `distr_db` d\n" +
                "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
                "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                "where pc.`date` >= ? and pc.`date` < ?\n" +
                "order by 1;";
    }

    @Override
    protected List<Distr> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Region> allRegions = DaoFactory.getDAOFactory().getRegionDAO().findAll();
        List<Distr> distrList = new ArrayList<>();
        while (resultSet.next()) {
            int region_id = resultSet.getInt("region_id");
            Distr distr = new Distr(resultSet.getInt("distr_id"),
                    resultSet.getString("distr_name"),
                    allRegions.stream()
                            .filter(region -> region.getId() == region_id)
                            .findFirst()
                            .get()
            );
            distrList.add(distr);
        }

        return distrList;
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Distr object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Distr object) {

    }

    @Override
    protected void prepareStatementForRemove(PreparedStatement statement, Distr object) throws SQLException {

    }

    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, LocalDate startDate, LocalDate endDate) throws SQLException {
        endDate = endDate.plusDays(1);
        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
    }

    @Override
    public List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate) {
        String sql = getFindAllByParametersQuery();
        List<Distr> list;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            prepareStatementForFindAllByParameters(statement, startDate, endDate);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }
}