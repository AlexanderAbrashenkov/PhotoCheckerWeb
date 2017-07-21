package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.model.nst.NstObl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class NstOblDaoSpringImpl implements NstOblDao {

    private final String SQL_FIND_ALL = "SELECT * FROM `nst_obl`";

    private final String SQL_FIND_BY_ID = "SELECT * FROM `nst_obl` WHERE id = ?";

    private final String SQL_FIND_BY_DATES = "SELECT DISTINCT obl.`name`, obl.`id`\n" +
            "FROM `nst_obl` obl\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`obl_id` = obl.`id`\n" +
            "INNER JOIN `nst_photo` pc ON pc.`client_id` = cc.`id`\n" +
            "WHERE pc.`date` >= ? AND pc.`date` < ?\n" +
            "AND cc.`format_id` = ?\n" +
            "ORDER BY 1;";

    //language=SQL
    private final String SQL_FIND_ALL_SIMPLE = "SELECT DISTINCT obl.`name`, obl.`id`\n" +
            "FROM `nst_obl` obl\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`obl_id` = obl.`id`\n" +
            "INNER JOIN %s pc ON pc.`client_id` = cc.`id`\n" +
            "WHERE cc.`format_id` = ?\n" +
            "ORDER BY 1;";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private Properties properties;

    @Autowired
    public NstOblDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nst_obl")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<NstObl> nstOblRowMapper = (rs, rowNum) -> {
        return new NstObl(rs.getInt("id"),
                rs.getString("name"));
    };

    @Override
    public int save(NstObl nstObl) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", nstObl.getName());

        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public NstObl find(int id) {
        List<NstObl> result = jdbcTemplate.query(SQL_FIND_BY_ID, nstOblRowMapper,
                id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NstObl> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, nstOblRowMapper);
    }

    @Override
    public boolean update(NstObl nstObl) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstObl nstObl) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstObl> findAllByDates(LocalDate startDate, LocalDate endDate, int formatId, int repTypeInd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {
            String sql = String.format(SQL_FIND_ALL_SIMPLE, photoTableName);
            return jdbcTemplate.query(sql, nstOblRowMapper,
                    formatId);
        } else {
            return jdbcTemplate.query(SQL_FIND_BY_DATES, nstOblRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate.plusDays(1)),
                    formatId);
        }
    }
}
