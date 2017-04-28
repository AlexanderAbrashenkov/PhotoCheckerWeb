package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.DistrDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Distr;
import com.photochecker.model.Region;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by market6 on 27.04.2017.
 */
public class DistrDAOMySqlImpl implements DistrDAO {

    @Override
    public boolean create(Distr distr) {
        return false;
    }

    @Override
    public Distr find(int id) {
        Distr distr = null;

        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `distr_db`\n" +
                    "WHERE `distr_id` = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int regionId = resultSet.getInt("region_id");
                Region region = DAOFactory.getDAOFactory().getRegionDAO().find(regionId);
                distr = new Distr(id,
                        resultSet.getString("distr_name"),
                        region);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distr;
    }

    @Override
    public List<Distr> findAll() {
        List<Distr> distrList = new ArrayList<>();
        try {
            List<Region> allRegions = DAOFactory.getDAOFactory().getRegionDAO().findAll();

            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `distr_db`");
            ResultSet resultSet = statement.executeQuery();

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

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distrList;
    }

    @Override
    public List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate) {
        List<Distr> distrList = new ArrayList<>();
        endDate = endDate.plusDays(1);
        try {
            List<Region> allRegions = DAOFactory.getDAOFactory().getRegionDAO().findAll();

            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("select distinct d.`distr_name`, d.`distr_id`, d.`region_id` \n" +
                    "from `distr_db` d\n" +
                    "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int region_id = resultSet.getInt("region_id");
                Region region = allRegions.stream()
                        .filter(region1 -> region1.getId() == region_id)
                        .findFirst()
                        .get();
                Distr distr = new Distr(resultSet.getInt("distr_id"),
                        resultSet.getString("distr_name"),
                        region);
                distrList.add(distr);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distrList;
    }
}
