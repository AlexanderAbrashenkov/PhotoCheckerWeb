package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.model.nst.NstFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class NstFormatDaoSpringImpl implements NstFormatDao {

    //language=SQL
    private final String SQL_FIND_BY_DATES = "SELECT DISTINCT f.`name`, f.`id`\n" +
            "FROM `nst_format` f\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`format_id` = f.`id`\n" +
            "INNER JOIN `photo_card` pc ON pc.`client_id` = cc.`id`\n" +
            "WHERE pc.`date` >= ? AND pc.`date` < ?\n" +
            "AND pc.`report_type` = ?\n" +
            "ORDER BY 1;";

    //language=SQL
    private final String SQL_FIND_BY_ID = "SELECT * FROM nst_format WHERE id = ?";

    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM nst_format";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NstFormatDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstFormat> nstFormatRowMapper = (rs, rowNum) -> {
        return new NstFormat(rs.getInt("id"),
                rs.getString("name"));
    };

    @Override
    public int save(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public NstFormat find(int id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, nstFormatRowMapper, id);
    }

    @Override
    public List<NstFormat> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, nstFormatRowMapper);
    }

    @Override
    public boolean update(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstFormat> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        return jdbcTemplate.query(SQL_FIND_BY_DATES, nstFormatRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                repTypeInd);
    }
}
