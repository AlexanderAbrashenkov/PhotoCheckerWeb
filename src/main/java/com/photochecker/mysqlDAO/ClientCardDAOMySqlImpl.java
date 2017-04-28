package com.photochecker.mysqlDAO;

import com.photochecker.dao.ClientCardDAO;
import com.photochecker.dao.DAOFactory;
import com.photochecker.model.ClientCard;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class ClientCardDAOMySqlImpl implements ClientCardDAO {
    @Override
    public boolean create(ClientCard clientCard) {
        return false;
    }

    @Override
    public ClientCard find(int id) {
        return null;
    }

    @Override
    public List<ClientCard> findAll() {
        return null;
    }

    @Override
    public boolean update(ClientCard clientCard) {
        return false;
    }

    @Override
    public List<ClientCard> findAllByDatesAndLka(Lka lka, LocalDate startDate, LocalDate endDate) {
        List<ClientCard> clientCardList = new ArrayList<>();
        endDate = endDate.plusDays(1);
        try {
            List<Distr> distrList = DAOFactory.getDAOFactory().getDistrDAO().findAll();
            List<Lka> lkaList = DAOFactory.getDAOFactory().getLkaDAO().findAll();

            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct cc.`client_id`, cc.`client_name`, cc.`client_address`, cc.`type_name`, \n" +
                    "case when sav.`save_date` is not null then 1 else 0 end checked,\n" +
                    "cc.`obl`, cc.`distributor_id`, cc.`channel_id`, cc.`lka_id`" +
                    "from `client_card` cc\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "left join (select * from save_lka_db slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`lka_id` = ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate.minusDays(1)));
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(endDate));
            statement.setInt(5, lka.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int distrId = resultSet.getInt("distributor_id");
                Distr distr = distrList.stream()
                        .filter(distr1 -> distr1.getId() == distrId)
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

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientCardList;
    }
}
