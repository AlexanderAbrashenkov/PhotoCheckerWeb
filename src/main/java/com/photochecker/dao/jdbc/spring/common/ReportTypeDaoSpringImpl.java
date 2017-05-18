package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by market6 on 18.05.2017.
 */
public class ReportTypeDaoSpringImpl implements ReportTypeDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_ID = "SELECT * FROM `report_type`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `report_type`";

    private final String SQL_FIND_BY_PARAMS = "SELECT r.`id`, r.`type`\n" +
            "FROM `report_type` r\n" +
            "INNER JOIN `report_type_user` ru ON ru.`report_type` = r.`id`\n" +
            "where ru.`user_id` = ?";

    public ReportTypeDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ReportType> reportTypeRowMapper = (resultSet, i) -> {
        return new ReportType(resultSet.getInt("id"),
                resultSet.getString("type"));
    };

    @Override
    public int save(ReportType reportType) {
        return 0;
    }

    @Override
    public ReportType find(int id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, reportTypeRowMapper, id).get(0);
    }

    @Override
    public List<ReportType> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, reportTypeRowMapper);
    }

    @Override
    public boolean update(ReportType reportType) {
        return false;
    }

    @Override
    public void remove(ReportType reportType) {

    }

    @Override
    public List<ReportType> findAllByUser(User user) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, reportTypeRowMapper, user.getId());
    }
}
