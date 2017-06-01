package com.photochecker.dao.jdbc.spring.mlka;

import com.photochecker.dao.mlka.MlkaClientCriteriasDao;
import com.photochecker.model.mlka.MlkaClientCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public class MlkaClientCriteriasDaoSpringImpl implements MlkaClientCriteriasDao {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_SAVE = "INSERT INTO `save_mlka_db`\n" +
            "(`save_date`, " +
            "`mz_photo`, `mz_corr`, `mz_crit1`, `mz_crit2`, `mz_crit3`, " +
            "`k_photo`, `k_corr`, `k_crit1`, `k_crit2`, `k_crit3`, " +
            "`s_photo`, `s_corr`, `s_crit1`, `s_crit2`, `s_crit3`, " +
            "`oos`, `comm`, " +
            "`client_id`, `date_from`, `date_to`)\n" +
            "VALUES (?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?," +
            "?, ?, ?);";

    //language=SQL
    private final String SQL_UPDATE = "UPDATE `save_mlka_db` SET\n" +
            "`save_date` = ?, " +
            "`mz_photo` = ?, `mz_corr` = ?, `mz_crit1` = ?, `mz_crit2` = ?, `mz_crit3` = ?, " +
            "`k_photo` = ?, `k_corr` = ?, `k_crit1` = ?, `k_crit2` = ?, `k_crit3` = ?, " +
            "`s_photo` = ?, `s_corr` = ?, `s_crit1` = ?, `s_crit2` = ?, `s_crit3` = ?, " +
            "`oos` = ?, `comm` = ? " +
            "WHERE `client_id` = ?\n " +
            "AND `date_from` = ?\n " +
            "AND `date_to` = ?";

    //language=SQL
    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `save_mlka_db`\n" +
            "WHERE `client_id` = ?\n" +
            "AND `date_from` = ?\n" +
            "AND `date_to` = ?";

    @Autowired
    public MlkaClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<MlkaClientCriterias> mlkaClientCriteriasRowMapper = (rs, rowNum) -> {
        return new MlkaClientCriterias(rs.getInt("client_id"),
                rs.getDate("date_from").toLocalDate(),
                rs.getDate("date_to").toLocalDate(),
                rs.getTimestamp("save_date").toLocalDateTime(),

                rs.getBoolean("mz_photo"),
                rs.getBoolean("mz_corr"),
                rs.getBoolean("mz_crit1"),
                rs.getBoolean("mz_crit2"),
                rs.getBoolean("mz_crit3"),

                rs.getBoolean("k_photo"),
                rs.getBoolean("k_corr"),
                rs.getBoolean("k_crit1"),
                rs.getBoolean("k_crit2"),
                rs.getBoolean("k_crit3"),

                rs.getBoolean("s_photo"),
                rs.getBoolean("s_corr"),
                rs.getBoolean("s_crit1"),
                rs.getBoolean("s_crit2"),
                rs.getBoolean("s_crit3"),

                rs.getBoolean("oos"),
                rs.getString("comm"));
    };

    @Override
    public int save(MlkaClientCriterias mlkaClientCriterias) {
        return jdbcTemplate.update(SQL_SAVE,
                Timestamp.valueOf(mlkaClientCriterias.getSaveDate()),
                mlkaClientCriterias.isMzPhoto(),
                mlkaClientCriterias.isMzCorr(),
                mlkaClientCriterias.isMzCrit1(),
                mlkaClientCriterias.isMzCrit2(),
                mlkaClientCriterias.isMzCrit3(),

                mlkaClientCriterias.iskPhoto(),
                mlkaClientCriterias.iskCorr(),
                mlkaClientCriterias.iskCrit1(),
                mlkaClientCriterias.iskCrit2(),
                mlkaClientCriterias.iskCrit3(),

                mlkaClientCriterias.issPhoto(),
                mlkaClientCriterias.issCorr(),
                mlkaClientCriterias.issCrit1(),
                mlkaClientCriterias.issCrit2(),
                mlkaClientCriterias.issCrit3(),

                mlkaClientCriterias.isOos(),
                mlkaClientCriterias.getComment(),

                mlkaClientCriterias.getClientId(),
                Date.valueOf(mlkaClientCriterias.getDateFrom()),
                Date.valueOf(mlkaClientCriterias.getDateTo()));
    }

    @Override
    public MlkaClientCriterias find(int id) {
        throw new RuntimeException("Method not used");
    }

    @Override
    public List<MlkaClientCriterias> findAll() {
        throw new RuntimeException("Method not used");
    }

    @Override
    public boolean update(MlkaClientCriterias mlkaClientCriterias) {
        jdbcTemplate.update(SQL_UPDATE,
                Timestamp.valueOf(mlkaClientCriterias.getSaveDate()),
                mlkaClientCriterias.isMzPhoto(),
                mlkaClientCriterias.isMzCorr(),
                mlkaClientCriterias.isMzCrit1(),
                mlkaClientCriterias.isMzCrit2(),
                mlkaClientCriterias.isMzCrit3(),

                mlkaClientCriterias.iskPhoto(),
                mlkaClientCriterias.iskCorr(),
                mlkaClientCriterias.iskCrit1(),
                mlkaClientCriterias.iskCrit2(),
                mlkaClientCriterias.iskCrit3(),

                mlkaClientCriterias.issPhoto(),
                mlkaClientCriterias.issCorr(),
                mlkaClientCriterias.issCrit1(),
                mlkaClientCriterias.issCrit2(),
                mlkaClientCriterias.issCrit3(),

                mlkaClientCriterias.isOos(),
                mlkaClientCriterias.getComment(),

                mlkaClientCriterias.getClientId(),
                Date.valueOf(mlkaClientCriterias.getDateFrom()),
                Date.valueOf(mlkaClientCriterias.getDateTo()));
        return true;
    }

    @Override
    public void remove(MlkaClientCriterias mlkaClientCriterias) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<MlkaClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, mlkaClientCriteriasRowMapper,
                clientId,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
