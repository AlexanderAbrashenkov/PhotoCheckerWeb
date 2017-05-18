package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ClientCardDao;
import com.photochecker.model.ClientCard;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 18.05.2017.
 */
public class ClientCardDaoSpringImpl implements ClientCardDao {

    //language=SQL
    private final String SQL_FIND_BY_PARAMS = "select distinct cc.`client_id`, cc.`client_name`, cc.`client_address`, cc.`type_name`, \n" +
            "case when sav.`save_date` is not null then 1 else 0 end checked,\n" +
            "cc.`obl`, cc.`distributor_id`, cc.`channel_id`, cc.`lka_id`" +
            "from `client_card` cc\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "left join (select * from save_lka_db slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`lka_id` = ?\n" +
            "order by 1;";

    private JdbcTemplate jdbcTemplate;
    private List<Distr> distrList;
    private List<Lka> lkaList;

    private RowMapper<ClientCard> clientCardRowMapper = (resultSet, i) -> {

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

        return new ClientCard(
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
    };

    public ClientCardDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void setClientCardFields() {
        distrList = DaoFactory.getDAOFactory().getDistrDAO().findAll();
        lkaList = DaoFactory.getDAOFactory().getLkaDAO().findAll();
    }

    @Override
    public int save(ClientCard clientCard) {
        return 0;
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
    public void remove(ClientCard clientCard) {

    }

    @Override
    public List<ClientCard> findAllByLkaAndDates(Lka lka, LocalDate startDate, LocalDate endDate) {
        setClientCardFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, clientCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.minusDays(1)),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                lka.getId());
    }
}
