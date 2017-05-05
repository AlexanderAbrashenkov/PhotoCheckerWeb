package com.photochecker.mysqlDAO.lka;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.lka.LkaReportItemDAO;
import com.photochecker.model.Lka;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.model.lka.LkaReportItem;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public class LkaReportItemDAOMySqlImpl implements LkaReportItemDAO {

    @Override
    public List<LkaReportItem> findAllByDate(LocalDate dateFrom, LocalDate dateTo, int repType) {
        List<LkaReportItem> lkaReportItemList = new ArrayList<>();
        dateTo = dateTo.plusDays(1);
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("select distinct r.region_name, d.distr_name, c.lka_id, l.lka_name, c.type_name, c.client_id, c.client_name, c.client_address,\n" +
                    "s.*, date_format(p.date, '%Y-%m-%d') as 'photo_date'\n" +
                    "from client_card c\n" +
                    "left join photo_card p on p.client_id = c.client_id\n" +
                    "left join \n" +
                    "(select * from save_lka_db where\n" +
                    "date_from >= ? and date_to < ?\n" +
                    ") s on s.client_id = c.client_id and s.date_from = date_format(p.date, '%Y-%m-%d')\n" +
                    "left join region_db r on c.region_id = r.region_id\n" +
                    "left join distr_db d on d.distr_id = c.distributor_id\n" +
                    "left join lka_db l on l.lka_id = c.lka_id\n" +
                    "where \n" +
                    "p.`date` >= ? and p.`date` < ?\n" +
                    "and p.report_type = ?\n" +
                    "order by r.region_name, d.distr_name, c.lka_id, c.client_id, photo_date");
            statement.setDate(1, Date.valueOf(dateFrom));
            statement.setDate(2, Date.valueOf(dateTo));
            statement.setDate(3, Date.valueOf(dateFrom));
            statement.setDate(4, Date.valueOf(dateTo));
            statement.setInt(5, repType);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientCriterias clientCriterias = new ClientCriterias(
                        resultSet.getInt("client_id"),
                        resultSet.getDate("date_from") != null ? resultSet.getDate("date_from").toLocalDate() : null,
                        resultSet.getDate("date_to") != null ? resultSet.getDate("date_to").toLocalDate() : null,
                        resultSet.getTimestamp("save_date") != null ? resultSet.getTimestamp("save_date").toLocalDateTime() : null,

                        resultSet.getBoolean("has_mz"),
                        resultSet.getBoolean("has_photo_mz"),
                        resultSet.getBoolean("is_correct_mz"),
                        resultSet.getBoolean("has_add_prod_mz"),
                        resultSet.getBoolean("crit1_mz"),
                        resultSet.getBoolean("crit2_mz"),

                        resultSet.getBoolean("has_k"),
                        resultSet.getBoolean("has_photo_k"),
                        resultSet.getBoolean("is_correct_k"),
                        resultSet.getBoolean("crit1_k"),
                        resultSet.getBoolean("crit2_k"),

                        resultSet.getBoolean("has_s"),
                        resultSet.getBoolean("has_photo_s"),
                        resultSet.getBoolean("is_correct_s"),
                        resultSet.getBoolean("crit1_s"),
                        resultSet.getBoolean("crit2_s"),

                        resultSet.getBoolean("has_m"),
                        resultSet.getBoolean("has_photo_m"),
                        resultSet.getBoolean("is_correct_m"),
                        resultSet.getBoolean("crit1_m"),
                        resultSet.getBoolean("crit2_m"),

                        resultSet.getBoolean("oos"),
                        resultSet.getString("comm"));

                LkaReportItem lkaReportItem = new LkaReportItem(
                        resultSet.getString("region_name"),
                        resultSet.getString("distr_name"),
                        resultSet.getInt("lka_id"),
                        resultSet.getString("lka_name"),
                        resultSet.getString("type_name"),
                        resultSet.getInt("client_id"),
                        resultSet.getString("client_name"),
                        resultSet.getString("client_address"),
                        clientCriterias,
                        resultSet.getDate("photo_date").toLocalDate()
                );

                lkaReportItemList.add(lkaReportItem);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaReportItemList;
    }
}
