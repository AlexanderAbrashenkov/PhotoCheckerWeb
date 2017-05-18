package com.photochecker.dao.jdbc.mysql.lka;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.model.Lka;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.dao.jdbc.mysql.AbstractDaoMySqlImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class LkaCriteriasDaoMySqlImpl extends AbstractDaoMySqlImpl<LkaCriterias> implements LkaCriteriasDao {

    @Override
    public String getSaveQuery() {
        return "INSERT INTO `lka_criterias_db`\n" +
                "(`lka_id`, `crit1_name`, `crit1_mz`, `crit1_k`, `crit1_s`, `crit1_m`, `crit2_name`)\n" +
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
    public String getRemoveQuery() {
        return "DELETE FROM `lka_criterias_db`\n" +
                "WHERE `lka_id` = ?";
    }

    @Override
    public String getFindAllByParametersQuery() {
        return null;
    }

    @Override
    protected List<LkaCriterias> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Lka> lkaList = DaoFactory.getDAOFactory().getLkaDAO().findAll();

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
    protected void prepareStatementForSave(PreparedStatement statement, LkaCriterias object) throws SQLException {
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
    protected void prepareStatementForRemove(PreparedStatement statement, LkaCriterias object) throws SQLException {
        statement.setInt(1, object.getLka().getId());
    }
}
