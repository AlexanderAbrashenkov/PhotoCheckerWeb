package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstReportItemDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstClientCriterias;
import com.photochecker.model.nst.NstReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class NstReportItemDaoSpringImpl implements NstReportItemDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "SELECT DISTINCT obl.name as nstObl, c.name as nstClient, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "  LEFT JOIN photo_card p ON p.client_id = c.id\n" +
            "  LEFT JOIN\n" +
            "  (SELECT * FROM nst_save_db WHERE\n" +
            "     date_from = ? AND date_to = ?\n" +
            "  ) s ON s.client_id = c.id\n" +
            "  LEFT JOIN nst_obl obl ON obl.id = c.obl_id\n" +
            "  LEFT JOIN nst_format f ON f.id = c.format_id\n" +
            "WHERE\n" +
            "    (p.`date` >= ? AND p.`date` < ? AND p.report_type = ?)\n" +
            "  OR (s.date_from = ? AND s.date_to = ?)\n" +
            "ORDER BY f.name, obl.name, c.name";

    //language=SQL
    private final String SQL_FIND_BY_USER = "SELECT DISTINCT obl.name as nstObl, c.name as nstClient, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "  LEFT JOIN photo_card p ON p.client_id = c.id\n" +
            "  LEFT JOIN\n" +
            "  (SELECT * FROM nst_save_db WHERE\n" +
            "     date_from = ? AND date_to = ?\n" +
            "  ) s ON s.client_id = c.id\n" +
            "  INNER JOIN nst_obl obl ON obl.id = c.obl_id AND obl.id = ?\n" +
            "  INNER JOIN nst_format f ON f.id = c.format_id AND f.id = ?\n" +
            "WHERE\n" +
            "    (p.`date` >= ? AND p.`date` < ? AND p.report_type = ?)\n" +
            "  OR (s.date_from = ? AND s.date_to = ?)\n" +
            "ORDER BY f.name, obl.name, c.name";

    @Autowired
    public NstReportItemDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstReportItem> nstReportItemRowMapper = (rs, i) -> {
        NstClientCriterias clientCriterias = new NstClientCriterias(
                rs.getInt("client_id"),
                rs.getDate("date_from") != null ? rs.getDate("date_from").toLocalDate() : null,
                rs.getDate("date_to") != null ? rs.getDate("date_to").toLocalDate() : null,
                rs.getTimestamp("save_date") != null ? rs.getTimestamp("save_date").toLocalDateTime() : null,
                rs.getInt("visit_count"),

                rs.getBoolean("mz_matrix"),
                rs.getBoolean("mz_photo"),
                rs.getBoolean("mz_borders"),
                rs.getBoolean("mz_vert"),
                rs.getBoolean("mz_30"),
                rs.getBoolean("mz_center"),
                rs.getString("mz_comment"),

                rs.getBoolean("ks_matrix"),
                rs.getBoolean("ks_photo"),
                rs.getBoolean("ks_borders"),
                rs.getBoolean("ks_vert"),
                rs.getBoolean("ks_30"),
                rs.getBoolean("ks_center"),
                rs.getString("ks_comment"),

                rs.getBoolean("m_matrix"),
                rs.getBoolean("m_photo"),
                rs.getBoolean("m_borders"),
                rs.getBoolean("m_vert"),
                rs.getBoolean("m_center"),
                rs.getString("m_comment"));

        return new NstReportItem(
                rs.getString("nstObl"),
                rs.getString("nstClient"),
                rs.getString("nstFormat"),
                clientCriterias
        );
    };

    @Override
    public int save(NstReportItem nstReportItem) {
        return 0;
    }

    @Override
    public NstReportItem find(int id) {
        return null;
    }

    @Override
    public List<NstReportItem> findAll() {
        return null;
    }

    @Override
    public boolean update(NstReportItem nstReportItem) {
        return false;
    }

    @Override
    public void remove(NstReportItem nstReportItem) {

    }

    @Override
    public List<NstReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, nstReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                repType,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }

    @Override
    public List<NstReportItem> findAllByUserParams(User user, int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        return jdbcTemplate.query(SQL_FIND_BY_USER, nstReportItemRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                nstOblId,
                formatId,
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                repTypeInd,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
