package com.photochecker.mysqlDAO.lka;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.lka.LkaCriteriasDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Lka;
import com.photochecker.model.PersistException;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class LkaCriteriasDAOMySqlImpl extends AbstractDAOMySqlImpl<LkaCriterias> implements LkaCriteriasDAO {

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `lka_criterias_db`\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `lka_criterias_db`\n" +
                "WHERE `lka_id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `lka_criterias_db`";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `lka_criterias_db` SET\n" +
                "crit1_name = ?, crit1_mz = ?, crit1_k = ?, crit1_s = ?, crit1_m = ?, crit2_name = ?\n" +
                "WHERE lka_id = ?";
    }

    @Override
    public String getFindAllByParametersQuery() {
        return null;
    }

    @Override
    protected List<LkaCriterias> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<Lka> lkaList = DAOFactory.getDAOFactory().getLkaDAO().findAll();

        List<LkaCriterias> lkaCriteriasList = new ArrayList<>();

        while (resultSet.next()) {
            int lkaId = resultSet.getInt("lka_id");
            Lka lka = lkaList.stream()
                    .filter(lka1 -> lka1.getId() == lkaId)
                    .findFirst()
                    .get();
            LkaCriterias lkaCriterias = new LkaCriterias(
                    lka,
                    resultSet.getString("crit1_name"),
                    resultSet.getInt("crit1_mz"),
                    resultSet.getInt("crit1_k"),
                    resultSet.getInt("crit1_s"),
                    resultSet.getInt("crit1_m"),
                    resultSet.getString("crit2_name"));
            lkaCriteriasList.add(lkaCriterias);
        }
        return lkaCriteriasList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, LkaCriterias object) throws SQLException {
        statement.setInt(1, object.getLka().getId());
        statement.setString(2, object.getCrit1Name());
        statement.setInt(3, object.getCrit1Mz());
        statement.setInt(4, object.getCrit1K());
        statement.setInt(5, object.getCrit1S());
        statement.setInt(6, object.getCrit1M());
        statement.setString(7, object.getCrit2Name());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, LkaCriterias object) throws SQLException {
        statement.setString(1, object.getCrit1Name());
        statement.setInt(2, object.getCrit1Mz());
        statement.setInt(3, object.getCrit1K());
        statement.setInt(4, object.getCrit1S());
        statement.setInt(5, object.getCrit1M());
        statement.setString(6, object.getCrit2Name());
        statement.setInt(7, object.getLka().getId());
    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {

    }
}
