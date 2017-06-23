package com.photochecker.dao.lkaMa.springImpl;

import com.photochecker.dao.lkaMa.LkaMaReportItemDao;
import com.photochecker.model.lkaMa.LkaMaClientCriterias;
import com.photochecker.model.lkaMa.LkaMaReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class LkaMaReportItemDaoSpringImpl implements LkaMaReportItemDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "select distinct r.region_name, d.distr_name, c.lka_id, l.lka_name, c.type_name, c.client_id, c.client_name, c.client_address,\n" +
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
            "order by r.region_name, d.distr_name, c.lka_id, c.client_id, photo_date";

    @Autowired
    public LkaMaReportItemDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<LkaMaReportItem> lkaReportItemRowMapper = (resultSet, i) -> {
        LkaMaClientCriterias clientCriterias = new LkaMaClientCriterias(
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

        return new LkaMaReportItem(
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
    };

    @Override
    public int save(LkaMaReportItem lkaMaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public LkaMaReportItem find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<LkaMaReportItem> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(LkaMaReportItem lkaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(LkaMaReportItem lkaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<LkaMaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, lkaReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repType);
    }
}
