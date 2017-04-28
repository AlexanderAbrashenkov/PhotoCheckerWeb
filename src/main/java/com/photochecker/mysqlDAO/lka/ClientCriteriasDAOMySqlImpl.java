package com.photochecker.mysqlDAO.lka;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.lka.ClientCriteriasDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ClientCriteriasDAOMySqlImpl implements ClientCriteriasDAO {
    @Override
    public boolean create(ClientCriterias clientCriterias) {
        String query = "INSERT INTO `save_lka_db`\n" +
                "(`save_date`, " +
                "`has_mz`, `has_photo_mz`, `is_correct_mz`, `has_add_prod_mz`, `crit1_mz`, `crit2_mz`, " +
                "`has_k`, `has_photo_k`, `is_correct_k`, `crit1_k`, `crit2_k`, " +
                "`has_s`, `has_photo_s`, `is_correct_s`, `crit1_s`, `crit2_s`, " +
                "`has_m`, `has_photo_m`, `is_correct_m`, `crit1_m`, `crit2_m`, " +
                "`oos`, `comm`, " +
                "`client_id`, `date_from`, `date_to`)\n" +
                "VALUES (?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?," +
                "?, ?, ?);";

        return doQuery(query, clientCriterias);
    }

    @Override
    public ClientCriterias find(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ClientCriterias clientCriterias = null;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `save_lka_db`\n" +
                    "WHERE `client_id` = ?\n" +
                    "AND `date_from` = ?\n" +
                    "AND `date_to` = ?");
            statement.setInt(1, clientId);
            statement.setDate(2, Date.valueOf(dateFrom));
            statement.setDate(3, Date.valueOf(dateTo));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                clientCriterias = new ClientCriterias(clientId,
                        dateFrom,
                        dateTo,
                        resultSet.getTimestamp("save_date").toLocalDateTime(),

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
                        resultSet.getString("comm")
                );
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientCriterias;
    }

    @Override
    public List<ClientCriterias> findAll() {
        return null;
    }

    @Override
    public boolean update(ClientCriterias clientCriterias) {
        System.out.println(clientCriterias);
        String query = "UPDATE `save_lka_db` SET\n" +
                "`save_date` = ?, " +
                "`has_mz` = ?, `has_photo_mz` = ?, `is_correct_mz` = ?, `has_add_prod_mz` = ?, `crit1_mz` = ?, `crit2_mz` = ?, " +
                "`has_k` = ?, `has_photo_k` = ?, `is_correct_k` = ?, `crit1_k` = ?, `crit2_k` = ?, " +
                "`has_s` = ?, `has_photo_s` = ?, `is_correct_s` = ?, `crit1_s` = ?, `crit2_s` = ?, " +
                "`has_m` = ?, `has_photo_m` = ?, `is_correct_m` = ?, `crit1_m` = ?, `crit2_m` = ?, " +
                "`oos` = ?, `comm` = ? " +
                "WHERE `client_id` = ?\n " +
                "AND `date_from` = ?\n " +
                "AND `date_to` = ?";

        return doQuery(query, clientCriterias);
    }

    private boolean doQuery(String query, ClientCriterias clientCriterias) {
        boolean isSucceed = false;
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setTimestamp(1, Timestamp.valueOf(clientCriterias.getSaveDate()));

            statement.setBoolean(2, clientCriterias.isHasMz());
            statement.setBoolean(3, clientCriterias.isHasPhotoMz());
            statement.setBoolean(4, clientCriterias.isCorrectMz());
            statement.setBoolean(5, clientCriterias.isHasAddProdMz());
            statement.setBoolean(6, clientCriterias.isCrit1Mz());
            statement.setBoolean(7, clientCriterias.isCrit2Mz());

            statement.setBoolean(8, clientCriterias.isHasK());
            statement.setBoolean(9, clientCriterias.isHasPhotoK());
            statement.setBoolean(10, clientCriterias.isCorrectK());
            statement.setBoolean(11, clientCriterias.isCrit1K());
            statement.setBoolean(12, clientCriterias.isCrit2K());

            statement.setBoolean(13, clientCriterias.isHasS());
            statement.setBoolean(14, clientCriterias.isHasPhotoS());
            statement.setBoolean(15, clientCriterias.isCorrectS());
            statement.setBoolean(16, clientCriterias.isCrit1S());
            statement.setBoolean(17, clientCriterias.isCrit2S());

            statement.setBoolean(18, clientCriterias.isHasM());
            statement.setBoolean(19, clientCriterias.isHasPhotoM());
            statement.setBoolean(20, clientCriterias.isCorrectM());
            statement.setBoolean(21, clientCriterias.isCrit1M());
            statement.setBoolean(22, clientCriterias.isCrit2M());

            statement.setBoolean(23, clientCriterias.isOos());
            statement.setString(24, clientCriterias.getComment());

            statement.setInt(25, clientCriterias.getClientId());
            statement.setDate(26, Date.valueOf(clientCriterias.getDateFrom()));
            statement.setDate(27, Date.valueOf(clientCriterias.getDateTo()));

            System.out.println(Date.valueOf(clientCriterias.getDateTo()));

            statement.execute();

            statement.close();
            connection.close();
            isSucceed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSucceed;
    }
}
