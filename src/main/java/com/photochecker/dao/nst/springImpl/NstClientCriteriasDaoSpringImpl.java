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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NstClientCriteriasDaoSpringImpl implements NstClientCriteriasDao {

    private final String SQL_FIND_ALL_BY_DATES = "SELECT * FROM `nst_save_db` sav " +
            "WHERE sav.`date_from` = ? AND sav.`date_to` = ?";

    private final String SQL_FIND_BY_DATES_AND_ID = "SELECT * FROM `nst_save_db` sav " +
            "WHERE sav.`date_from` = ? AND sav.`date_to` = ? " +
            "AND sav.`client_id` = ?";

    private final String SQL_UPDATE = "UPDATE `nst_save_db` SET " +
            "save_date = ?, visit_count = ?, " +
            "mz_matrix = ?, mz_photo = ?, mz_borders = ?, mz_vert = ?, mz_30 = ?, mz_center = ?, mz_comment = ?, " +
            "ks_matrix = ?, ks_photo = ?, ks_borders = ?, ks_vert = ?, ks_30 = ?, ks_center = ?, ks_comment = ?, " +
            "m_matrix = ?, m_photo = ?, m_borders = ?, m_vert = ?, m_center = ?, m_comment = ? " +
            "WHERE client_id = ? " +
            "AND date_from = ? " +
            "AND date_to = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public NstClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nst_save_db")
                .usingGeneratedKeyColumns("id");
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
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", nstClientCriterias.getClientId());
        params.put("date_from", Date.valueOf(nstClientCriterias.getDateFrom()));
        params.put("date_to", Date.valueOf(nstClientCriterias.getDateTo()));
        params.put("save_date", Timestamp.valueOf(nstClientCriterias.getSaveDate()));
        params.put("visit_count", nstClientCriterias.getVisitCount());

        params.put("mz_matrix", nstClientCriterias.isMzMatrix());
        params.put("mz_photo", nstClientCriterias.isMzPhoto());
        params.put("mz_borders", nstClientCriterias.isMzBorders());
        params.put("mz_vert", nstClientCriterias.isMzVert());
        params.put("mz_30", nstClientCriterias.isMz30());
        params.put("mz_center", nstClientCriterias.isMzCenter());
        params.put("mz_comment", nstClientCriterias.getMzComment());

        params.put("ks_matrix", nstClientCriterias.isKsMatrix());
        params.put("ks_photo", nstClientCriterias.isKsPhoto());
        params.put("ks_borders", nstClientCriterias.isKsBorders());
        params.put("ks_vert", nstClientCriterias.isKsVert());
        params.put("ks_30", nstClientCriterias.isKs30());
        params.put("ks_center", nstClientCriterias.isKsCenter());
        params.put("ks_comment", nstClientCriterias.getKsComment());

        params.put("m_matrix", nstClientCriterias.ismMatrix());
        params.put("m_photo", nstClientCriterias.ismPhoto());
        params.put("m_borders", nstClientCriterias.ismBorders());
        params.put("m_vert", nstClientCriterias.ismVert());
        params.put("m_center", nstClientCriterias.ismCenter());
        params.put("m_comment", nstClientCriterias.getmComment());

        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
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
        jdbcTemplate.update(SQL_UPDATE,
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
        List<NstClientCriterias> result = jdbcTemplate.query(SQL_FIND_BY_DATES_AND_ID, nstClientCriteriasRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                clientId);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NstClientCriterias> findAllByDates(LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_DATES, nstClientCriteriasRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
