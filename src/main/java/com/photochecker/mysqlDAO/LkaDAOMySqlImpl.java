package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.LkaDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by market6 on 27.04.2017.
 */
public class LkaDAOMySqlImpl implements LkaDAO {
    @Override
    public boolean create(Lka lka) {
        return false;
    }

    @Override
    public Lka find(int id) {
        Lka lka = null;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `lka_db`\n" +
                    "WHERE `lka_id` = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lka = new Lka(id, resultSet.getString("lka_name"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lka;
    }

    @Override
    public List<Lka> findAll() {
        List<Lka> lkaList = new ArrayList<>();
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `lka_db`");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lka lka = new Lka(resultSet.getInt("lka_id"),
                        resultSet.getString("lka_name"));
                lkaList.add(lka);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaList;
    }

    @Override
    public List<Lka> findAllByDatesAndDistr(Distr distr, LocalDate startDate, LocalDate endDate) {
        List<Lka> lkaList = new ArrayList<>();
        endDate = endDate.plusDays(1);
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct lka.`lka_name`, lka.`lka_id` from `lka_db` lka\n" +
                    "inner join `client_card` cc on cc.`lka_id` = lka.`lka_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`region_id` = ?\n" +
                    "and cc.`distributor_id` = ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setInt(3, distr.getRegion().getId());
            statement.setInt(4, distr.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lka lka = new Lka(resultSet.getInt("lka_id"),
                        resultSet.getString("lka_name"));
                lkaList.add(lka);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaList;
    }
}
