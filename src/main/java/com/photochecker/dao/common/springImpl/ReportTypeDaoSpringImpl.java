package com.photochecker.dao.common.springImpl;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ReportTypeDaoSpringImpl implements ReportTypeDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_ID = "SELECT * FROM `report_type`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `report_type`";

    private final String SQL_FIND_BY_PARAMS = "SELECT r.`id`, r.`type`\n" +
            "FROM `report_type` r\n" +
            "INNER JOIN `report_type_user` ru ON ru.`report_type` = r.`id`\n" +
            "where ru.`user_id` = ?";

    private final String SQL_SAVE = "INSERT INTO `report_type` (`type`) VALUES (?)";



    @Autowired
    public ReportTypeDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ReportType> reportTypeRowMapper = (resultSet, i) -> {
        return new ReportType(resultSet.getInt("id"),
                resultSet.getString("type"));
    };

    @Override
    public int save(ReportType reportType) {
        return jdbcTemplate.update(SQL_SAVE, reportType.getName());
    }

    @Override
    public ReportType find(int id) {
        List<ReportType> result = jdbcTemplate.query(SQL_FIND_BY_ID, reportTypeRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<ReportType> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, reportTypeRowMapper);
    }

    @Override
    public boolean update(ReportType reportType) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(ReportType reportType) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<ReportType> findAllByUser(User user) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, reportTypeRowMapper, user.getId());
    }
}
