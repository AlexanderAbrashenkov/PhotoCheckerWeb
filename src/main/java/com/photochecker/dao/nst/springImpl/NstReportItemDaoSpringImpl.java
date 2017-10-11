package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstReportItemDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstClientCriterias;
import com.photochecker.model.nst.NstRepCrit;
import com.photochecker.model.nst.NstReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class NstReportItemDaoSpringImpl implements NstReportItemDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_PARAMS = "SELECT obl.id as oblId, obl.name as nstObl, c.id as nstClientId, c.name as nstClient, f.id as nstFormatId, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "  LEFT JOIN\n" +
            "  (SELECT * FROM nst_save_db WHERE\n" +
            "     date_from = ? AND date_to = ?\n" +
            "  ) s ON s.client_id = c.id\n" +
            "  LEFT JOIN nst_obl obl ON obl.id = c.obl_id\n" +
            "  LEFT JOIN nst_format f ON f.id = c.format_id\n" +
            "WHERE\n" +
            "  (s.date_from = ? AND s.date_to = ?)";

    //language=SQL
    private final String SQL_FIND_BY_PARAMS_SIMPLE = "SELECT obl.id as oblId, obl.name as nstObl, c.id as nstClientId, c.name as nstClient, f.id as nstFormatId, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "LEFT JOIN %s s ON s.client_id = c.id\n" +
            "LEFT JOIN nst_obl obl ON obl.id = c.obl_id\n" +
            "LEFT JOIN nst_format f ON f.id = c.format_id";

    //language=SQL
    private final String SQL_FIND_BY_USER = "SELECT obl.id as oblId, obl.name as nstObl, c.id as nstClientId, c.name as nstClient, f.id as nstFormatId, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "  LEFT JOIN\n" +
            "  (SELECT * FROM nst_save_db WHERE\n" +
            "     date_from = ? AND date_to = ?\n" +
            "  ) s ON s.client_id = c.id\n" +
            "  INNER JOIN nst_obl obl ON obl.id = c.obl_id AND obl.id = ?\n" +
            "  INNER JOIN nst_format f ON f.id = c.format_id AND f.id = ?\n" +
            "WHERE\n" +
            "  (s.date_from = ? AND s.date_to = ?)";

    //language=SQL
    private final String SQL_FIND_BY_USER_SIMPLE = "SELECT obl.id as oblId, obl.name as nstObl, c.id as nstClientId, c.name as nstClient, f.id as nstFormatId, f.name as nstFormat, s.*\n" +
            "FROM nst_client_card c\n" +
            "LEFT JOIN %s s ON s.client_id = c.id\n" +
            "INNER JOIN nst_obl obl ON obl.id = c.obl_id AND obl.id = ?\n" +
            "INNER JOIN nst_format f ON f.id = c.format_id AND f.id = ?";

    @Autowired
    private Properties properties;

    @Autowired
    public NstReportItemDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstReportItem> nstReportItemRowMapper = (rs, i) -> {

        boolean mzMatrix = rs.getBoolean("mz_matrix");
        boolean ksMatrix = rs.getBoolean("ks_matrix");
        boolean mMatrix = rs.getBoolean("m_matrix");
        int visitCount = rs.getInt("visit_count");

        NstRepCrit nstRepCrit = new NstRepCrit(
                rs.getInt("client_id"),
                rs.getDate("date_from") != null ? rs.getDate("date_from").toLocalDate() : null,
                rs.getDate("date_to") != null ? rs.getDate("date_to").toLocalDate() : null,
                (rs.getTimestamp("save_date") != null && visitCount >=0) ? rs.getTimestamp("save_date").toLocalDateTime() : null,
                visitCount,

                mzMatrix,
                mzMatrix ? (rs.getBoolean("mz_photo") ? "+" : "-") : null,
                mzMatrix ? (rs.getBoolean("mz_borders") ? "+" : "-") : null,
                mzMatrix ? (rs.getBoolean("mz_vert") ? "+" : "-") : null,
                mzMatrix ? (rs.getBoolean("mz_30") ? "+" : "-") : null,
                mzMatrix ? (rs.getBoolean("mz_center") ? "+" : "-") : null,
                rs.getString("mz_comment"),

                ksMatrix,
                ksMatrix ? (rs.getBoolean("ks_photo") ? "+" : "-") : null,
                ksMatrix ? (rs.getBoolean("ks_borders") ? "+" : "-") : null,
                ksMatrix ? (rs.getBoolean("ks_vert") ? "+" : "-") : null,
                ksMatrix ? (rs.getBoolean("ks_30") ? "+" : "-") : null,
                ksMatrix ? (rs.getBoolean("ks_center") ? "+" : "-") : null,
                rs.getString("ks_comment"),

                mMatrix,
                mMatrix ? (rs.getBoolean("m_photo") ? "+" : "-") : null,
                mMatrix ? (rs.getBoolean("m_borders") ? "+" : "-") : null,
                mMatrix ? (rs.getBoolean("m_vert") ? "+" : "-") : null,
                mMatrix ? (rs.getBoolean("m_center") ? "+" : "-") : null,
                rs.getString("m_comment"));

        return new NstReportItem(
                rs.getInt("oblId"),
                rs.getString("nstObl"),
                rs.getInt("nstClientId"),
                rs.getString("nstClient"),
                rs.getInt("nstFormatId"),
                rs.getString("nstFormat"),
                nstRepCrit
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
    public Set<NstReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";
        List<NstReportItem> fullList = new ArrayList<>();

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {

            String saveTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_save";
            String sql = String.format(SQL_FIND_BY_PARAMS_SIMPLE, saveTableName);
            fullList = jdbcTemplate.query(sql, nstReportItemRowMapper);

        } else {
            fullList = jdbcTemplate.query(SQL_FIND_BY_PARAMS, nstReportItemRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate),
                    Date.valueOf(startDate),
                    Date.valueOf(endDate.plusDays(1)),
                    repType,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate));
        }
        Set<NstReportItem> sortedUnicResult = new TreeSet<>();
        for (NstReportItem nstReportItem : fullList) {
            sortedUnicResult.add(nstReportItem);
        }
        return sortedUnicResult;
    }

    @Override
    public Set<NstReportItem> findAllByUserParams(User user, int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeInd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";
        List<NstReportItem> fullList = new ArrayList<>();

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {

            String saveTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_save";
            String sql = String.format(SQL_FIND_BY_USER_SIMPLE, saveTableName);
            fullList = jdbcTemplate.query(sql, nstReportItemRowMapper,
                    nstOblId,
                    formatId);
        } else {
            fullList = jdbcTemplate.query(SQL_FIND_BY_USER, nstReportItemRowMapper,
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
        Set<NstReportItem> sortedUnicResult = new TreeSet<>();
        for (NstReportItem nstReportItem : fullList) {
            sortedUnicResult.add(nstReportItem);
        }
        return sortedUnicResult;
    }
}
