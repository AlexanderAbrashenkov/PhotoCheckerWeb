package com.photochecker.dao.mlka.springImpl;

import com.photochecker.dao.mlka.MlkaReportItemDao;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.model.mlka.MlkaReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class MlkaReportItemDaoSpringImpl implements MlkaReportItemDao {

    private final String SQL_FIND_BY_PARAMS = "select distinct nka.name as nka_name, r.region_name, d.distr_name, emp.name as emp_name, " +
            "c.type_name, c.client_id, c.client_name, c.client_address, " +
            "s.*, date_format(p.date, '%Y-%m-%d') as 'photo_date' " +
            "from `client_card` c " +
            "INNER JOIN `nka_type_db` nka ON nka.`id` = c.`nka_type` " +
            "left join `photo_card` p on p.`client_id` = c.`client_id` " +
            "LEFT JOIN `employee_db` emp ON emp.`emp_id` = p.`employee_id` " +
            "left join " +
            "(select * from save_mlka_db where " +
            "date_from >= ? and date_to < ? " +
            ") s on s.client_id = c.client_id and s.date_from = date_format(p.date, '%Y-%m-%d') " +
            "left join region_db r on c.region_id = r.region_id " +
            "left join distr_db d on d.distr_id = c.distributor_id " +
            "where " +
            "p.`date` >= ? and p.`date` < ? " +
            "and p.report_type = ? " +
            "order by nka.name, emp.name, c.client_address, photo_date;";

    private JdbcTemplate jdbcTemplate;

    private RowMapper<MlkaReportItem> mlkaReportItemRowMapper = (resultSet, i) -> {
        MlkaClientCriterias clientCriterias = new MlkaClientCriterias(
                resultSet.getInt("client_id"),
                resultSet.getDate("date_from") != null ? resultSet.getDate("date_from").toLocalDate() : null,
                resultSet.getDate("date_to") != null ? resultSet.getDate("date_to").toLocalDate() : null,
                resultSet.getTimestamp("save_date") != null ? resultSet.getTimestamp("save_date").toLocalDateTime() : null,

                resultSet.getBoolean("mz_photo"),
                resultSet.getBoolean("mz_corr"),
                resultSet.getBoolean("mz_crit1"),
                resultSet.getBoolean("mz_crit2"),
                resultSet.getBoolean("mz_crit3"),

                resultSet.getBoolean("k_photo"),
                resultSet.getBoolean("k_corr"),
                resultSet.getBoolean("k_crit1"),
                resultSet.getBoolean("k_crit2"),
                resultSet.getBoolean("k_crit3"),

                resultSet.getBoolean("s_photo"),
                resultSet.getBoolean("s_corr"),
                resultSet.getBoolean("s_crit1"),
                resultSet.getBoolean("s_crit2"),
                resultSet.getBoolean("s_crit3"),

                resultSet.getBoolean("oos"),
                resultSet.getString("comm"));

        return new MlkaReportItem(
                resultSet.getString("nka_name"),
                resultSet.getString("region_name"),
                resultSet.getString("distr_name"),
                resultSet.getString("emp_name"),
                resultSet.getString("type_name"),
                resultSet.getInt("client_id"),
                resultSet.getString("client_name"),
                resultSet.getString("client_address"),
                clientCriterias,
                resultSet.getDate("photo_date").toLocalDate()
        );
    };

    @Autowired
    public MlkaReportItemDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(MlkaReportItem mlkaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public MlkaReportItem find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<MlkaReportItem> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(MlkaReportItem mlkaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(MlkaReportItem mlkaReportItem) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<MlkaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, mlkaReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repType);
    }
}
