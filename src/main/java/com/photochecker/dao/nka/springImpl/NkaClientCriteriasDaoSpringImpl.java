package com.photochecker.dao.nka.springImpl;

import com.photochecker.dao.nka.NkaClientCriteriasDao;
import com.photochecker.model.nka.NkaClientCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
public class NkaClientCriteriasDaoSpringImpl implements NkaClientCriteriasDao {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_SAVE = "INSERT INTO `save_nka_db`\n" +
            "(`save_date`, " +
            "`mz_dp`, `mz_bb`, `mz_mr`, `mz_comment`, " +
            "`k_dp`, `k_bb`, `k_mr`, `k_comment`, " +
            "`s_dp`, `s_bb`, `s_mr`, `s_comment`, " +
            "`mz_double`, `k_double`, `s_double`, " +
            "`mz_dm_a`, `k_dm_a`, `s_dm_a`, " +
            "`mz_dm_na`, `k_dm_na`, `s_dm_na`, " +
            "`client_id`, `date_from`, `date_to`)\n" +
            "VALUES (?, " +
            "?, ?, ?, ?, " +
            "?, ?, ?, ?, " +
            "?, ?, ?, ?, " +
            "?, ?, ?," +
            "?, ?, ?," +
            "?, ?, ?," +
            "?, ?, ?);";

    //language=SQL
    private final String SQL_UPDATE = "UPDATE `save_nka_db` SET\n" +
            "`save_date` = ?, " +
            "`mz_dp` = ?, `mz_bb` = ?, `mz_mr` = ?, `mz_comment` = ?, " +
            "`k_dp` = ?, `k_bb` = ?, `k_mr` = ?, `k_comment` = ?, " +
            "`s_dp` = ?, `s_bb` = ?, `s_mr` = ?, `s_comment` = ?, " +
            "`mz_double` = ?, `k_double` = ?, `s_double` = ?, " +
            "`mz_dm_a` = ?, `k_dm_a` = ?, `s_dm_a` = ?, " +
            "`mz_dm_na` = ?, `k_dm_na` = ?, `s_dm_na` = ?, " +
            "WHERE `client_id` = ?\n " +
            "AND `date_from` = ?\n " +
            "AND `date_to` = ?";

    //language=SQL
    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `save_nka_db`\n" +
            "WHERE `client_id` = ?\n" +
            "AND `date_from` = ?\n" +
            "AND `date_to` = ?";

    @Autowired
    public NkaClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NkaClientCriterias> nkaClientCriteriasRowMapper = (rs, rowNum) -> {
        return new NkaClientCriterias(rs.getInt("client_id"),
                rs.getDate("date_from").toLocalDate(),
                rs.getDate("date_to").toLocalDate(),
                rs.getTimestamp("save_date").toLocalDateTime(),

                rs.getBoolean("mz_dp"),
                rs.getBoolean("mz_bb"),
                rs.getBoolean("mz_mr"),
                rs.getString("mz_Comment"),

                rs.getBoolean("k_dp"),
                rs.getBoolean("k_bb"),
                rs.getBoolean("k_mr"),
                rs.getString("k_comment"),

                rs.getBoolean("s_dp"),
                rs.getBoolean("s_bb"),
                rs.getBoolean("s_mr"),
                rs.getString("s_comment"),

                rs.getBoolean("mz_double"),
                rs.getBoolean("k_double"),
                rs.getBoolean("s_double"),

                rs.getBoolean("mz_dm_a"),
                rs.getBoolean("k_dm_a"),
                rs.getBoolean("s_dm_a"),

                rs.getBoolean("mz_dm_na"),
                rs.getBoolean("k_dm_na"),
                rs.getBoolean("s_dm_na"));
    };

    @Override
    public int save(NkaClientCriterias nkaClientCriterias) {
        return jdbcTemplate.update(SQL_SAVE,
                Timestamp.valueOf(nkaClientCriterias.getSaveDate()),
                nkaClientCriterias.isMzDP(),
                nkaClientCriterias.isMzBB(),
                nkaClientCriterias.isMzMR(),
                nkaClientCriterias.getMzComment(),

                nkaClientCriterias.iskDP(),
                nkaClientCriterias.iskBB(),
                nkaClientCriterias.iskMR(),
                nkaClientCriterias.getkComment(),

                nkaClientCriterias.issDP(),
                nkaClientCriterias.issBB(),
                nkaClientCriterias.issMR(),
                nkaClientCriterias.getsComment(),

                nkaClientCriterias.isMzDouble(),
                nkaClientCriterias.iskDouble(),
                nkaClientCriterias.issDouble(),

                nkaClientCriterias.isMzDmA(),
                nkaClientCriterias.iskDmA(),
                nkaClientCriterias.issDmA(),

                nkaClientCriterias.isMzDmNa(),
                nkaClientCriterias.iskDmNa(),
                nkaClientCriterias.issDmNa(),

                nkaClientCriterias.getClientId(),
                Date.valueOf(nkaClientCriterias.getDateFrom()),
                Date.valueOf(nkaClientCriterias.getDateTo()));
    }

    @Override
    public NkaClientCriterias find(int id) {
        throw new RuntimeException("Method not used");
    }

    @Override
    public List<NkaClientCriterias> findAll() {
        throw new RuntimeException("Method not used");
    }

    @Override
    public boolean update(NkaClientCriterias nkaClientCriterias) {
        jdbcTemplate.update(SQL_UPDATE,
                Timestamp.valueOf(nkaClientCriterias.getSaveDate()),

                nkaClientCriterias.isMzDP(),
                nkaClientCriterias.isMzBB(),
                nkaClientCriterias.isMzMR(),
                nkaClientCriterias.getMzComment(),

                nkaClientCriterias.iskDP(),
                nkaClientCriterias.iskBB(),
                nkaClientCriterias.iskMR(),
                nkaClientCriterias.getkComment(),

                nkaClientCriterias.issDP(),
                nkaClientCriterias.issBB(),
                nkaClientCriterias.issMR(),
                nkaClientCriterias.getsComment(),

                nkaClientCriterias.isMzDouble(),
                nkaClientCriterias.iskDouble(),
                nkaClientCriterias.issDouble(),

                nkaClientCriterias.isMzDmA(),
                nkaClientCriterias.iskDmA(),
                nkaClientCriterias.issDmA(),

                nkaClientCriterias.isMzDmNa(),
                nkaClientCriterias.iskDmNa(),
                nkaClientCriterias.issDmNa(),

                nkaClientCriterias.getClientId(),
                Date.valueOf(nkaClientCriterias.getDateFrom()),
                Date.valueOf(nkaClientCriterias.getDateTo()));
        return true;
    }

    @Override
    public void remove(NkaClientCriterias nkaClientCriterias) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NkaClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, nkaClientCriteriasRowMapper,
                clientId,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
