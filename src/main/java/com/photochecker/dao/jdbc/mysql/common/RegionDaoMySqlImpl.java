package com.photochecker.dao.jdbc.mysql.common;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.model.Region;
import com.photochecker.dao.jdbc.mysql.AbstractDaoMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class RegionDaoMySqlImpl extends AbstractDaoMySqlImpl<Region> implements RegionDao {


    @Override
    public String getSaveQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `region_db`\n" +
                "WHERE `region_id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `region_db`";
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
        return "select distinct r.`region_name`, r.`region_id` from `region_db` r\n" +
                "inner join `client_card` cc on cc.`region_id` = r.`region_id`\n" +
                "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                "where pc.`date` >= ? and pc.`date` < ?\n" +
                "order by 1;";
    }

    @Override
    protected List<Region> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Region> regionList = new ArrayList<>();
        while (resultSet.next()) {
            Region region = new Region(resultSet.getInt("region_id"),
                    resultSet.getString("region_name").trim());
            regionList.add(region);
        }
        return regionList;
    }

    @Override
    protected void prepareStatementForSave(PreparedStatement statement, Region object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Region object) {

    }

    @Override
    protected void prepareStatementForRemove(PreparedStatement statement, Region object) throws SQLException {

    }

    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, LocalDate startDate, LocalDate endDate) throws SQLException {
        endDate = endDate.plusDays(1);

        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
    }

    @Override
    public List<Region> findAllByDates(LocalDate startDate, LocalDate endDate) {
        String sql = getFindAllByParametersQuery();
        List<Region> list;

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
