package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.common.LkaDAO;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import com.photochecker.model.PersistException;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class LkaDAOMySqlImpl extends AbstractDAOMySqlImpl<Lka> implements LkaDAO {

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM `lka_db`\n" +
                "WHERE `lka_id` = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM `lka_db`";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "select distinct lka.`lka_name`, lka.`lka_id` from `lka_db` lka\n" +
                "inner join `client_card` cc on cc.`lka_id` = lka.`lka_id`\n" +
                "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                "where pc.`date` >= ? and pc.`date` < ?\n" +
                "and cc.`region_id` = ?\n" +
                "and cc.`distributor_id` = ?\n" +
                "order by 1;";
    }

    @Override
    protected List<Lka> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<Lka> lkaList = new ArrayList<>();
        while (resultSet.next()) {
            Lka lka = new Lka(resultSet.getInt("lka_id"),
                    resultSet.getString("lka_name"));
            lkaList.add(lka);
        }
        return lkaList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Lka object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Lka object) {

    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {
        Distr distr = (Distr) params[0];
        LocalDate startDate = (LocalDate) params[1];
        LocalDate endDate = (LocalDate) params[2];
        endDate = endDate.plusDays(1);

        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
        statement.setInt(3, distr.getRegion().getId());
        statement.setInt(4, distr.getId());
    }
}
