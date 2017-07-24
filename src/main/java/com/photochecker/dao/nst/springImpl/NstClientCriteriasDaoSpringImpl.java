package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstClientCriteriasDao;
import com.photochecker.model.nst.NstClientCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class NstClientCriteriasDaoSpringImpl implements NstClientCriteriasDao {

    private final String SQL_FIND_ALL_BY_DATES = "SELECT * FROM `nst_save_db` sav " +
            "WHERE sav.`date_from` = ? AND sav.`date_to` = ?";

    //language=SQL
    private final String SQL_FIND_ALL_SIMPLE = "SELECT  * FROM %s";

    private final String SQL_FIND_BY_DATES_AND_ID = "SELECT * FROM `nst_save_db` sav " +
            "WHERE sav.`date_from` = ? AND sav.`date_to` = ? " +
            "AND sav.`client_id` = ?";

    //language=SQL
    private final String SQL_FIND_BY_ID_SIMPLE = "SELECT * FROM %s sav " +
            "WHERE sav.`client_id` = ?";

    //language=SQL
    private final String SQL_UPDATE = "UPDATE %s SET " +
            "save_date = ?, visit_count = ?, " +
            "mz_matrix = ?, mz_photo = ?, mz_borders = ?, mz_vert = ?, mz_30 = ?, mz_center = ?, mz_comment = ?, " +
            "ks_matrix = ?, ks_photo = ?, ks_borders = ?, ks_vert = ?, ks_30 = ?, ks_center = ?, ks_comment = ?, " +
            "m_matrix = ?, m_photo = ?, m_borders = ?, m_vert = ?, m_center = ?, m_comment = ? " +
            "WHERE client_id = ? " +
            "AND date_from = ? " +
            "AND date_to = ?";

    //language=SQL
    private final String SQL_SAVE = "INSERT INTO %s " +
            "(client_id, date_from, date_to, save_date, visit_count, " +
            "mz_matrix, mz_photo, mz_borders, mz_vert, mz_30, mz_center, mz_comment, " +
            "ks_matrix, ks_photo, ks_borders, ks_vert, ks_30, ks_center, ks_comment, " +
            "m_matrix, m_photo, m_borders, m_vert, m_center, m_comment) " +
            "VALUES (?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?)";

    //language=SQL
    private final String SQL_COPY_DATA_FROM_TABLE = "INSERT INTO nst_save_db (client_id, date_from, date_to, save_date, visit_count, " +
            "mz_matrix, mz_photo, mz_borders, mz_vert, mz_30, mz_center, mz_comment, " +
            "ks_matrix, ks_photo, ks_borders, ks_vert, ks_30, ks_center, ks_comment, " +
            "m_matrix, m_photo, m_borders, m_vert, m_center, m_comment)  " +
            "SELECT " +
            "client_id, date_from, date_to, save_date, visit_count, " +
            "mz_matrix, mz_photo, mz_borders, mz_vert, mz_30, mz_center, mz_comment, " +
            "ks_matrix, ks_photo, ks_borders, ks_vert, ks_30, ks_center, ks_comment, " +
            "m_matrix, m_photo, m_borders, m_vert, m_center, m_comment " +
            "FROM %s";

    //language=SQL
    private final String SQL_DROP_TABLE = "DROP TABLE %s";

    //language=SQL
    private final String SQL_CREATE_NEW_TABLE = "CREATE TABLE %s LIKE `nst_save_db`";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Properties properties;

    @Autowired
    public NstClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstClientCriterias> nstClientCriteriasRowMapper = (rs, rowNum) -> {
        return new NstClientCriterias(rs.getInt("client_id"),
                rs.getDate("date_from").toLocalDate(),
                rs.getDate("date_to").toLocalDate(),
                rs.getTimestamp("save_date").toLocalDateTime(),
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
    };

    @Override
    public int save(NstClientCriterias nstClientCriterias) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String saveTableName = nstClientCriterias.getDateFrom().format(formatter) + "_"
                + nstClientCriterias.getDateTo().format(formatter) + "_nst_save";

        if (saveTableName.equals(properties.getProperty("nst.current.week.save")) ||
                saveTableName.equals(properties.getProperty("nst.prev.week.save"))) {
            /* NONE */
        } else {
            saveTableName = "nst_save_db";
        }

        String sql = String.format(SQL_SAVE, saveTableName);

        return jdbcTemplate.update(sql,
                nstClientCriterias.getClientId(),
                Date.valueOf(nstClientCriterias.getDateFrom()),
                Date.valueOf(nstClientCriterias.getDateTo()),
                Timestamp.valueOf(nstClientCriterias.getSaveDate()),
                nstClientCriterias.getVisitCount(),

                nstClientCriterias.isMzMatrix(),
                nstClientCriterias.isMzPhoto(),
                nstClientCriterias.isMzBorders(),
                nstClientCriterias.isMzVert(),
                nstClientCriterias.isMz30(),
                nstClientCriterias.isMzCenter(),
                nstClientCriterias.getMzComment(),

                nstClientCriterias.isKsMatrix(),
                nstClientCriterias.isKsPhoto(),
                nstClientCriterias.isKsBorders(),
                nstClientCriterias.isKsVert(),
                nstClientCriterias.isKs30(),
                nstClientCriterias.isKsCenter(),
                nstClientCriterias.getKsComment(),

                nstClientCriterias.ismMatrix(),
                nstClientCriterias.ismPhoto(),
                nstClientCriterias.ismBorders(),
                nstClientCriterias.ismVert(),
                nstClientCriterias.ismCenter(),
                nstClientCriterias.getmComment());
    }

    @Override
    public NstClientCriterias find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstClientCriterias> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(NstClientCriterias nstClientCriterias) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String saveTableName = nstClientCriterias.getDateFrom().format(formatter) + "_"
                + nstClientCriterias.getDateTo().format(formatter) + "_nst_save";

        if (saveTableName.equals(properties.getProperty("nst.current.week.save")) ||
                saveTableName.equals(properties.getProperty("nst.prev.week.save"))) {
            /* NONE */
        } else {
            saveTableName = "nst_save_db";
        }

        String sql = String.format(SQL_UPDATE,saveTableName);

        jdbcTemplate.update(sql,
                Timestamp.valueOf(nstClientCriterias.getSaveDate()),
                nstClientCriterias.getVisitCount(),

                nstClientCriterias.isMzMatrix(),
                nstClientCriterias.isMzPhoto(),
                nstClientCriterias.isMzBorders(),
                nstClientCriterias.isMzVert(),
                nstClientCriterias.isMz30(),
                nstClientCriterias.isMzCenter(),
                nstClientCriterias.getMzComment(),

                nstClientCriterias.isKsMatrix(),
                nstClientCriterias.isKsPhoto(),
                nstClientCriterias.isKsBorders(),
                nstClientCriterias.isKsVert(),
                nstClientCriterias.isKs30(),
                nstClientCriterias.isKsCenter(),
                nstClientCriterias.getKsComment(),

                nstClientCriterias.ismMatrix(),
                nstClientCriterias.ismPhoto(),
                nstClientCriterias.ismBorders(),
                nstClientCriterias.ismVert(),
                nstClientCriterias.ismCenter(),
                nstClientCriterias.getmComment(),

                nstClientCriterias.getClientId(),
                Date.valueOf(nstClientCriterias.getDateFrom()),
                Date.valueOf(nstClientCriterias.getDateTo()));
        return true;
    }

    @Override
    public void remove(NstClientCriterias nstClientCriterias) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public NstClientCriterias findByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String saveTableName = startDate.format(formatter) + "_"
                + endDate.format(formatter) + "_nst_save";

        if (saveTableName.equals(properties.getProperty("nst.current.week.save")) ||
                saveTableName.equals(properties.getProperty("nst.prev.week.save"))) {

            String sql = String.format(SQL_FIND_BY_ID_SIMPLE, saveTableName);
            List<NstClientCriterias> result = jdbcTemplate.query(sql, nstClientCriteriasRowMapper,
                    clientId);
            return result.size() > 0 ? result.get(0) : null;
        } else {
            List<NstClientCriterias> result = jdbcTemplate.query(SQL_FIND_BY_DATES_AND_ID, nstClientCriteriasRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate),
                    clientId);
            return result.size() > 0 ? result.get(0) : null;
        }
    }

    @Override
    public List<NstClientCriterias> findAllByDates(LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String saveTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_save";

        if (saveTableName.equals(properties.getProperty("nst.current.week.save"))
                || saveTableName.equals(properties.getProperty("nst.prev.week.save"))) {
            String sql = String.format(SQL_FIND_ALL_SIMPLE, saveTableName);
            return jdbcTemplate.query(sql, nstClientCriteriasRowMapper);
        } else {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_DATES, nstClientCriteriasRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate));
        }
    }

    @Override
    public int copyCritsToCommon(String saveTableName) {
        String sql1 = String.format(SQL_COPY_DATA_FROM_TABLE, saveTableName);
        int rows = jdbcTemplate.update(sql1);

        String sql = String.format(SQL_DROP_TABLE, saveTableName);
        jdbcTemplate.update(sql);
        return rows;
    }

    @Override
    public boolean createCurrentTable(String saveTableName) {
        String sql = String.format(SQL_CREATE_NEW_TABLE, saveTableName);
        int rows = jdbcTemplate.update(sql);
        return true;
    }
}
