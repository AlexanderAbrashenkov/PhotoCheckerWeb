package com.photochecker.dao.nka.springImpl;

import com.photochecker.dao.nka.NkaReportItemDao;
import com.photochecker.model.nka.NkaReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class NkaReportItemDaoSpringImpl implements NkaReportItemDao {
    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "select distinct c.lka_id, l.lka_name, f. name as type_name, c.client_id as cl_id, c.client_name, c.client_address,\n" +
            "s.*, par.*\n" +
            "from client_card c\n" +
            "LEFT JOIN nka_param par on par.nka_id = c.lka_id\n" +
            "left join photo_card p on p.client_id = c.client_id\n" +
            "left join\n" +
            "(select * from save_nka_db where\n" +
            "date_from = ? and date_to = ?\n" +
            ") s on s.client_id = c.client_id\n" +
            "left join lka_db l on l.lka_id = c.lka_id\n" +
            "left join format_type f on f.id = c.format_id\n" +
            "where\n" +
            "p.`date` >= ? and p.`date` < ?\n" +
            "and p.report_type = ?\n" +
            "and p.employee_id = ?";

    @Autowired
    public NkaReportItemDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NkaReportItem> nkaReportItemRowMapper = (rs, i) -> {
        int mzPlan = 0;
        if (rs.getString("mz_dp_short") != null && rs.getString("mz_dp_short").length() > 1) {
            mzPlan++;
        }
        if (rs.getString("mz_bb_short") != null && rs.getString("mz_bb_short").length() > 1) {
            mzPlan++;
        }
        if (rs.getString("mz_mr_short") != null && rs.getString("mz_mr_short").length() > 1) {
            mzPlan++;
        }

        int kPlan = 0;
        if (rs.getString("k_dp_short") != null && rs.getString("k_dp_short").length() > 1) {
            kPlan++;
        }
        if (rs.getString("k_bb_short") != null && rs.getString("k_bb_short").length() > 1) {
            kPlan++;
        }
        if (rs.getString("k_mr_short") != null && rs.getString("k_mr_short").length() > 1) {
            kPlan++;
        }

        int sPlan = 0;
        if (rs.getString("s_dp_short") != null && rs.getString("s_dp_short").length() > 1) {
            sPlan++;
        }
        if (rs.getString("s_bb_short") != null && rs.getString("s_bb_short").length() > 1) {
            sPlan++;
        }
        if (rs.getString("s_mr_short") != null && rs.getString("s_mr_short").length() > 1) {
            sPlan++;
        }

        NkaReportItem nkaReportItem = new NkaReportItem(rs.getInt("lka_id"),
                rs.getString("lka_name"),
                rs.getString("type_name"),
                rs.getInt("cl_id"),
                rs.getString("client_address"),
                mzPlan,
                kPlan,
                sPlan,
                rs.getInt("mz_dp"),
                rs.getInt("mz_bb"),
                rs.getInt("mz_mr"),
                rs.getString("mz_comment"),
                rs.getInt("k_dp"),
                rs.getInt("k_bb"),
                rs.getInt("k_mr"),
                rs.getString("k_comment"),
                rs.getInt("s_dp"),
                rs.getInt("s_bb"),
                rs.getInt("s_mr"),
                rs.getString("s_comment"),
                rs.getInt("mz_double"),
                rs.getInt("k_double"),
                rs.getInt("s_double"),
                rs.getInt("mz_dm_a") +
                        rs.getInt("k_dm_a") +
                        rs.getInt("s_dm_a"),
                rs.getInt("mz_dm_na") +
                        rs.getInt("k_dm_na") +
                        rs.getInt("s_dm_na")
        );
        return nkaReportItem;
    };

    @Override
    public List<NkaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int employee_id, int repType) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, nkaReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                repType,
                employee_id
                );
    }
}
