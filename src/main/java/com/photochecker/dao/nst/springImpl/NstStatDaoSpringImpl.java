package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstStatDao;
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
public class NstStatDaoSpringImpl implements NstStatDao {

    //language=SQL
    private final String SQL_SELECT_TT_COUNT = "SELECT " +
            "  count(DISTINCT c.name) AS count " +
            "FROM nst_client_card c " +
            "  LEFT JOIN photo_card p ON p.client_id = c.id " +
            "  INNER JOIN nst_obl obl ON obl.id = c.obl_id and obl.id = ? " +
            "  INNER JOIN nst_format f ON f.id = c.format_id and f.id = ? " +
            "WHERE " +
            "  (p.`date` >= ? AND p.`date` < ? AND p.report_type = ?)";

    //language=SQL
    private final String SQL_SELECT_SAVED_DATES = "SELECT " +
            "  s.save_date " +
            "FROM nst_client_card c " +
            "  LEFT JOIN " +
            "  (SELECT * " +
            "   FROM nst_save_db " +
            "   WHERE " +
            "     date_from = ? AND date_to = ? " +
            "  ) s ON s.client_id = c.id " +
            "  INNER JOIN nst_obl obl ON obl.id = c.obl_id and obl.id = ? " +
            "  INNER JOIN nst_format f ON f.id = c.format_id and f.id = ? " +
            "WHERE " +
            "  s.date_from = ? AND s.date_to = ? " +
            "AND s.visit_count != -1";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NstStatDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Integer> countRowMapper = (rs, rowNum) -> {
        return rs.getInt("count");
    };

    private RowMapper<LocalDateTime> savedDatesRowMapper = (rs, rowNum) -> {
        return rs.getTimestamp("save_date").toLocalDateTime();
    };

    @Override
    public int getTtCount(int formatId, int oblId, LocalDate startDate, LocalDate endDate, int repType) {
        List<Integer> countList = jdbcTemplate.query(SQL_SELECT_TT_COUNT, countRowMapper,
                oblId,
                formatId,
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                repType);
        return countList.size() > 0 ? countList.get(0) : 0;
    }

    @Override
    public List<LocalDateTime> getSavedDates(int formatId, int oblId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_SELECT_SAVED_DATES, savedDatesRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                oblId,
                formatId,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
