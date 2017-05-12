package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.common.ClientCardDAO;
import com.photochecker.dao.DAOFactory;
import com.photochecker.model.ClientCard;
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
public class ClientCardDAOMySqlImpl extends AbstractDAOMySqlImpl<ClientCard> implements ClientCardDAO {

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return null;
    }

    @Override
    public String getFindAllQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "select distinct cc.`client_id`, cc.`client_name`, cc.`client_address`, cc.`type_name`, \n" +
                "case when sav.`save_date` is not null then 1 else 0 end checked,\n" +
                "cc.`obl`, cc.`distributor_id`, cc.`channel_id`, cc.`lka_id`" +
                "from `client_card` cc\n" +
                "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                "left join (select * from save_lka_db slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
                "where pc.`date` >= ? and pc.`date` < ?\n" +
                "and cc.`lka_id` = ?\n" +
                "order by 1;";
    }

    @Override
    protected List<ClientCard> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<Distr> distrList = DAOFactory.getDAOFactory().getDistrDAO().findAll();
        List<Lka> lkaList = DAOFactory.getDAOFactory().getLkaDAO().findAll();
        List<ClientCard> clientCardList = new ArrayList<>();

        while (resultSet.next()) {
            int distrId = resultSet.getInt("distributor_id");
            Distr distr = distrList.stream()
                    .filter(distr1 -> distr1.getId() == distrId)
                    .findFirst()
                    .get();

            int lkaId = resultSet.getInt("lka_id");
            Lka lka = lkaList.stream()
                    .filter(lka1 -> lka1.getId() == lkaId)
                    .findFirst()
                    .get();

            ClientCard clientCard = new ClientCard(
                    resultSet.getInt("client_id"),
                    resultSet.getString("client_name"),
                    resultSet.getString("client_address"),
                    resultSet.getString("type_name"),
                    resultSet.getBoolean("checked"),
                    distr,
                    resultSet.getString("obl"),
                    resultSet.getInt("channel_id"),
                    lka
            );
            clientCardList.add(clientCard);
        }
        return clientCardList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, ClientCard object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ClientCard object) {

    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object... params) throws SQLException {
        Lka lka = (Lka) params[0];
        LocalDate startDate = (LocalDate) params[1];
        LocalDate endDate = (LocalDate) params[2];
        endDate = endDate.plusDays(1);
        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate.minusDays(1)));
        statement.setDate(3, Date.valueOf(startDate));
        statement.setDate(4, Date.valueOf(endDate));
        statement.setInt(5, lka.getId());
    }
}
