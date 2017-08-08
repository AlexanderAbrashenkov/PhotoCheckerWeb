package com.photochecker.dao.lka.springImpl;

import com.photochecker.dao.lka.LkaReportItemDao;
import com.photochecker.model.lka.LkaReportItem;
import com.photochecker.model.lka.ReportCrit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LkaReportItemDaoSpringImpl implements LkaReportItemDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "select distinct r.region_name, d.distr_name, c.lka_id, l.lka_name, f.name as type_name, c.client_id, c.client_name, c.client_address,\n" +
            "s.*, date_format(p.date, '%Y-%m-%d') as 'photo_date', p.employee_id as empId, e.name as empName\n" +
            "from client_card c\n" +
            "left join photo_card p on p.client_id = c.client_id\n" +
            "left join \n" +
            "(select * from save_lka_db where\n" +
            "date_from >= ? and date_to < ?\n" +
            ") s on s.client_id = c.client_id and s.date_from = date_format(p.date, '%Y-%m-%d')\n" +
            "left join region_db r on c.region_id = r.region_id\n" +
            "left join distr_db d on d.distr_id = c.distributor_id\n" +
            "left join lka_db l on l.lka_id = c.lka_id\n" +
            "left join format_type f on f.id = c.format_id\n" +
            "LEFT JOIN employee_db e on e.emp_id = p.employee_id\n" +
            "where \n" +
            "p.`date` >= ? and p.`date` < ?\n" +
            "and p.report_type = ?\n" +
            "order by r.region_name, d.distr_name, c.lka_id, c.client_id, photo_date";

    @Autowired
    public LkaReportItemDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<LkaReportItem> lkaReportItemRowMapper = (resultSet, i) -> {

        boolean hasMz = resultSet.getBoolean("has_mz");
        boolean hasK = resultSet.getBoolean("has_k");
        boolean hasS = resultSet.getBoolean("has_s");
        boolean hasM = resultSet.getBoolean("has_m");

        LocalDateTime saveDate = resultSet.getTimestamp("save_date") != null ? resultSet.getTimestamp("save_date").toLocalDateTime() : null;

        ReportCrit clientCriterias = new ReportCrit(
                resultSet.getInt("client_id"),
                resultSet.getDate("date_from") != null ? resultSet.getDate("date_from").toLocalDate() : null,
                resultSet.getDate("date_to") != null ? resultSet.getDate("date_to").toLocalDate() : null,
                saveDate,

                hasMz,
                hasMz ? resultSet.getInt("has_photo_mz") : null,
                hasMz ? resultSet.getInt("is_correct_mz") : null,
                hasMz ? resultSet.getInt("has_add_prod_mz") : null,
                hasMz ? resultSet.getInt("crit1_mz") : null,
                hasMz ? resultSet.getInt("crit2_mz") : null,

                hasK,
                hasK ? resultSet.getInt("has_photo_k") : null,
                hasK ? resultSet.getInt("is_correct_k") : null,
                hasK ? resultSet.getInt("crit1_k") : null,
                hasK ? resultSet.getInt("crit2_k") : null,

                hasS,
                hasS ? resultSet.getInt("has_photo_s") : null,
                hasS ? resultSet.getInt("is_correct_s") : null,
                hasS ? resultSet.getInt("crit1_s") : null,
                hasS ? resultSet.getInt("crit2_s") : null,

                hasM,
                hasS ? resultSet.getInt("has_photo_m") : null,
                hasS ? resultSet.getInt("is_correct_m") : null,
                hasS ? resultSet.getInt("crit1_m") : null,
                hasS ? resultSet.getInt("crit2_m") : null,

                resultSet.getInt("oos"),
                resultSet.getString("comm"));

        return new LkaReportItem(
                resultSet.getString("region_name").trim(),
                resultSet.getString("distr_name"),
                resultSet.getInt("lka_id"),
                resultSet.getString("lka_name"),
                resultSet.getString("type_name"),
                resultSet.getInt("client_id"),
                resultSet.getString("client_name"),
                resultSet.getString("client_address"),
                clientCriterias,
                resultSet.getDate("photo_date").toLocalDate(),
                resultSet.getInt("empId"),
                resultSet.getString("empName"),
                saveDate != null ? 1 : 0
        );
    };

    @Override
    public List<LkaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, lkaReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repType);
    }
}
