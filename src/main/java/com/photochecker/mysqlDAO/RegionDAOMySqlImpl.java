package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.RegionDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Region;
import com.photochecker.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by market6 on 27.04.2017.
 */
public class RegionDAOMySqlImpl implements RegionDAO {

    @Override
    public boolean create(Region region) {
        return false;
    }

    @Override
    public Region find(int id) {
        Region region = null;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `region_db`\n" +
                    "WHERE `region_id` = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                region = new Region(resultSet.getInt("region_id"),
                        resultSet.getString("region_name"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return region;
    }

    @Override
    public List<Region> findAll() {
        List<Region> regionList = new ArrayList<>();
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `region_db`");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Region region = new Region(resultSet.getInt("region_id"),
                        resultSet.getString("region_name"));
                regionList.add(region);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionList;
    }

    @Override
    public List<Region> findAllByDates(LocalDate startDate, LocalDate endDate) {
        List<Region> regionList = new ArrayList<>();
        endDate = endDate.plusDays(1);
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("select distinct r.`region_name`, r.`region_id` from `region_db` r\n" +
                    "inner join `client_card` cc on cc.`region_id` = r.`region_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Region region = new Region(resultSet.getInt("region_id"),
                        resultSet.getString("region_name").trim());
                regionList.add(region);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionList;
    }
}
