package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstStatDao;
import com.photochecker.model.nst.NstClientCard;
import com.photochecker.model.nst.NstStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Component
public class NstStatDaoSpringImpl implements NstStatDao {

    //language=SQL
    private final String SQL_SELECT_TOTAL_STAT = "SELECT sum(s.tt_count) as tt, sum(s.tt_checked) as checked, sum(s.tt_checked_today) as today\n" +
            "from nst_stat s\n" +
            "WHERE s.date_from = ?";

    //language=SQL
    private final String SQL_SELECT_OBL_STAT = "SELECT sum(s.tt_count) as tt, sum(s.tt_checked) as checked, sum(s.tt_checked_today) as today\n" +
            "from nst_stat s\n" +
            "WHERE s.date_from = ?\n" +
            "AND s.format_id = ?\n" +
            "and s.obl_id = ?";

    //language=SQL
    private final String SQL_CLEAR_CHECKED_TODAY = "UPDATE nst_stat " +
            "SET tt_checked_today = 0";

    //language=SQL
    private final String SQL_COUNT_STAT = "INSERT INTO nst_stat (date_from, date_to, format_id, obl_id, tt_count)\n" +
            "SELECT ? as date_from, ? as date_to, c.format_id, c.obl_id, count(DISTINCT p.client_id) as tt_count\n" +
            "FROM nst_client_card c\n" +
            "JOIN %s p on p.client_id = c.id\n" +
            "GROUP BY c.format_id, c.obl_id";

    //language=SQL
    private final String SQL_DELETE_STAT = "DELETE FROM nst_stat " +
            "WHERE date_from = ?";

    //language=SQL
    private final String SQL_INCREASE_STAT = "UPDATE nst_stat " +
            "SET tt_checked = tt_checked + 1, tt_checked_today = tt_checked_today + 1 " +
            "WHERE format_id = ? " +
            "AND obl_id = ? " +
            "AND date_from = ?";

    //language=SQL
    private final String SQL_INCREASE_TODAY = "UPDATE nst_stat " +
            "SET tt_checked_today = tt_checked_today + 1 " +
            "WHERE format_id = ? " +
            "AND obl_id = ? " +
            "AND date_from = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Properties properties;

    @Autowired
    public NstStatDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstStat> totalStatRowMapper = (rs, rowNum) -> new NstStat(
            rs.getInt("tt"),
            rs.getInt("checked"),
            rs.getInt("today"),
            0,
            0,
            0
    );

    private RowMapper<NstStat> oblStatRowMapper = (rs, rowNum) -> new NstStat(
            0,
            0,
            0,
            rs.getInt("tt"),
            rs.getInt("checked"),
            rs.getInt("today")
    );

    @Override
    public NstStat getTotalStat(LocalDate startDate, LocalDate endDate) {
        List<NstStat> totalStatList = jdbcTemplate.query(SQL_SELECT_TOTAL_STAT, totalStatRowMapper,
                Date.valueOf(startDate));
        return totalStatList.size() > 0 ? totalStatList.get(0) : new NstStat();
    }

    @Override
    public NstStat getOblStat(int formatId, int oblId, LocalDate startDate, LocalDate endDate) {
        List<NstStat> oblStatList = jdbcTemplate.query(SQL_SELECT_OBL_STAT, oblStatRowMapper,
                startDate,
                formatId,
                oblId);
        return oblStatList.size() > 0 ? oblStatList.get(0) : new NstStat();
    }

    @Override
    public int clearCheckedToday() {
        return jdbcTemplate.update(SQL_CLEAR_CHECKED_TODAY);
    }

    @Override
    public void fillUpWeekStat(LocalDate startDate, LocalDate endDate) {
        jdbcTemplate.update(SQL_DELETE_STAT, Date.valueOf(startDate));

        String sqlInsert = String.format(SQL_COUNT_STAT, properties.getProperty("nst.current.week.photo"));
        jdbcTemplate.update(sqlInsert, Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Override
    public void increaseChecked(int formatId, int oblId, LocalDate startDate, LocalDate endDate) {
        jdbcTemplate.update(SQL_INCREASE_STAT, formatId, oblId, Date.valueOf(startDate));
    }

    @Override
    public void increaseCheckedToday(int formatId, int oblId, LocalDate startDate, LocalDate endDate) {
        jdbcTemplate.update(SQL_INCREASE_TODAY, formatId, oblId, Date.valueOf(startDate));
    }
}
