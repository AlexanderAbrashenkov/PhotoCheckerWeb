package com.photochecker.mysqlDAO.lka;

import com.photochecker.dao.lka.LkaCriteriasDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Lka;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class LkaCriteriasDAOMySqlImpl implements LkaCriteriasDAO {
    @Override
    public boolean create(LkaCriterias lkaCriterias) {
        return false;
    }

    @Override
    public LkaCriterias find(Lka lka) {
        LkaCriterias lkaCriterias = null;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `lka_criterias_db`\n" +
                    "WHERE `lka_id` = ?");
            statement.setInt(1, lka.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lkaCriterias = new LkaCriterias(
                        lka,
                        resultSet.getString("crit1_name"),
                        resultSet.getInt("crit1_mz"),
                        resultSet.getInt("crit1_k"),
                        resultSet.getInt("crit1_s"),
                        resultSet.getInt("crit1_m"),
                        resultSet.getString("crit2_name"));
            } else {
                lkaCriterias = new LkaCriterias(lka, "Доля полки",
                        10, 10, 10, 10,
                        "Бренд-блок");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaCriterias;
    }

    @Override
    public List<LkaCriterias> findAll() {
        return null;
    }

    @Override
    public boolean update(LkaCriterias lkaCriterias) {
        return false;
    }
}
