package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 18.05.2017.
 */
public class LkaDaoSpringImpl implements LkaDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_ID = "SELECT * FROM `lka_db`\n" +
            "WHERE `lka_id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `lka_db`";

    private final String SQL_FIND_BY_PARAMS = "select distinct lka.`lka_name`, lka.`lka_id` from `lka_db` lka\n" +
            "inner join `client_card` cc on cc.`lka_id` = lka.`lka_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`region_id` = ?\n" +
            "and cc.`distributor_id` = ?\n" +
            "order by 1;";

    @Autowired
    public LkaDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Lka> lkaRowMapper = (resultSet, i) -> {
        return new Lka(resultSet.getInt("lka_id"),
                resultSet.getString("lka_name"));
    };

    @Override
    public int save(Lka lka) {
        return 0;
    }

    @Override
    public Lka find(int id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, lkaRowMapper, id).get(0);
    }

    @Override
    public List<Lka> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, lkaRowMapper);
    }

    @Override
    public boolean update(Lka lka) {
        return false;
    }

    @Override
    public void remove(Lka lka) {

    }

    @Override
    public List<Lka> findAllByDistrAndDates(Distr distr, LocalDate startDate, LocalDate endDate) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, lkaRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                distr.getRegion().getId(),
                distr.getId());
    }
}
