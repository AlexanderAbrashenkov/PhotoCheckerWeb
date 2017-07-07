package com.photochecker.dao.lkaDmp.springImpl;

import com.photochecker.dao.lkaDmp.DmpReportItemDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.model.lkaDmp.DmpReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class DmpReportItemDaoSpringImpl implements DmpReportItemDao {
    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "select distinct r.region_name, d.distr_name, ch.name as channel, c.lka_id, l.lka_name, c.type_name, c.client_id, c.client_name, c.client_address, " +
    "s.* " +
    "from client_card c " +
    "left join photo_card p on p.client_id = c.client_id " +
    "left join " +
    "(select * from save_lka_dmp_db where " +
    "date_from = ? and date_to = ? " +
    ") s on s.client_id = c.client_id " +
    "left join region_db r on c.region_id = r.region_id " +
    "left join distr_db d on d.distr_id = c.distributor_id " +
    "left join lka_db l on l.lka_id = c.lka_id " +
    "left join channel_db ch on ch.id = c.channel_id " +
    "where " +
    "p.`date` >= ? and p.`date` < ? " +
    "and p.report_type = ? " +
    "order by r.region_name, d.distr_name, channel, c.lka_id, c.client_id, dmp_num";

    @Autowired
    public DmpReportItemDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<DmpReportItem> dmpReportItemRowMapper = (resultSet, i) -> {
        DmpClientCriterias clientCriterias = new DmpClientCriterias (
                resultSet.getInt("client_id"),
                resultSet.getDate("date_from") != null ? resultSet.getDate("date_from").toLocalDate() : null,
                resultSet.getDate("date_to") != null ? resultSet.getDate("date_to").toLocalDate() : null,
                resultSet.getTimestamp("save_date") != null ? resultSet.getTimestamp("save_date").toLocalDateTime() : null,

                resultSet.getInt("dmp_count") != 0 ? resultSet.getInt("dmp_num") : -1,
                resultSet.getInt("dmp_count"),
                resultSet.getBoolean("is_photo_corr"),
                resultSet.getBoolean("keyword"),

                resultSet.getBoolean("mz"),
                resultSet.getBoolean("mz_ricco"),
                resultSet.getBoolean("mz_r_spec"),
                resultSet.getBoolean("mz_milad"),
                resultSet.getBoolean("mz_m_spec"),
                resultSet.getBoolean("k"),
                resultSet.getBoolean("s"),
                resultSet.getBoolean("s_spec"),
                resultSet.getBoolean("m"),
                resultSet.getBoolean("m_spec"),

                resultSet.getBoolean("min_size"),
                resultSet.getBoolean("tma_prod"),
                resultSet.getBoolean("price"),
                resultSet.getBoolean("fill80"),
                resultSet.getBoolean("place"),

                resultSet.getString("comm"));

        return new DmpReportItem(
                resultSet.getString("region_name"),
                resultSet.getString("distr_name"),
                resultSet.getString("channel"),
                resultSet.getInt("lka_id"),
                resultSet.getString("lka_name"),
                resultSet.getString("type_name"),
                resultSet.getInt("client_id"),
                resultSet.getString("client_name"),
                resultSet.getString("client_address"),
                clientCriterias,
                null
        );
    };

    @Override
    public List<DmpReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, dmpReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.minusDays(1)),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repType);
    }
}
