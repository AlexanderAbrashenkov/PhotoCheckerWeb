package com.photochecker.dao.common.springImpl;

import com.photochecker.dao.common.FormatTypeDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.common.Distr;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.common.Lka;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FormatTypeDaoSpringImpl implements FormatTypeDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private final String SQL_FIND_BY_ID = "SELECT * FROM `format_type`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `format_type`";

    @Autowired
    public FormatTypeDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("format_type")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<FormatType> formatTypeRowMapper = (resultSet, i) -> {
        return new FormatType(resultSet.getInt("id"),
                resultSet.getString("name"));
    };

    @Override
    public int save(FormatType formatType) {
        Map<String, String> params = new HashMap<>();
        params.put("name", formatType.getName());
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public FormatType find(int id) {
        List<FormatType> result = jdbcTemplate.query(SQL_FIND_BY_ID, formatTypeRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<FormatType> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, formatTypeRowMapper);
    }

    @Override
    public boolean update(FormatType formatType) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(FormatType formatType) {
        throw new RuntimeException("This method not used");
    }
}
