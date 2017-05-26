package com.photochecker.dao.jdbc.spring.lkaDmp;

import com.photochecker.dao.lkaDmp.DmpClientCriteriasDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 26.05.2017.
 */
public class DmpClientCriteriasDaoSpringImpl implements DmpClientCriteriasDao {

    private final String SQL_SAVE = "INSERT INTO `save_lka_dmp_db`\n" +
            "(`save_date`, " +
            "`dmp_num`, `dmp_count`, `is_photo_corr`, `keyword`, " +
            "`mz`, `k`, `add_prod`, `s`, `m`, " +
            "`min_size`, `tma_prod`, `price`, `fill80`, `place`, " +
            "`comm`, " +
            "`client_id`, `date_from`, `date_to`)\n" +
            "VALUES (?, " +
            "?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, " +
            "?, ?, ?);";

    //language = SQL
    private final String SQL_REMOVE = "DELETE FROM `save_lka_dmp_db` " +
            "WHERE `client_id` = ? " +
            "AND `date_from` = ? AND `date_to` = ?";

    //language = SQL
    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `save_lka_dmp_db`\n" +
            "WHERE `client_id` = ?\n" +
            "AND `date_from` = ?\n" +
            "AND `date_to` = ?";


    private JdbcTemplate jdbcTemplate;


    private RowMapper<DmpClientCriterias> dmpClientCriteriasRowMapper = (resultSet, i) -> {
        return new DmpClientCriterias(
                resultSet.getInt("client_id"),
                resultSet.getDate("date_from").toLocalDate(),
                resultSet.getDate("date_to").toLocalDate(),
                resultSet.getTimestamp("save_date").toLocalDateTime(),

                resultSet.getInt("dmp_num"),
                resultSet.getInt("dmp_count"),
                resultSet.getBoolean("is_photo_corr"),
                resultSet.getBoolean("keyword"),

                resultSet.getBoolean("mz"),
                resultSet.getBoolean("k"),
                resultSet.getBoolean("add_prod"),
                resultSet.getBoolean("s"),
                resultSet.getBoolean("m"),

                resultSet.getBoolean("min_size"),
                resultSet.getBoolean("tma_prod"),
                resultSet.getBoolean("price"),
                resultSet.getBoolean("fill80"),
                resultSet.getBoolean("place"),

                resultSet.getString("comm")
        );
    };

    @Autowired
    public DmpClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //todo: return id of saved row
    @Override
    public int save(DmpClientCriterias clientCriterias) {
        return jdbcTemplate.update(SQL_SAVE,
                Timestamp.valueOf(clientCriterias.getSaveDate()),
                clientCriterias.getDmpNum(),
                clientCriterias.getDmpCount(),
                clientCriterias.isPhotoCorr(),
                clientCriterias.isKeyword(),

                clientCriterias.isMz(),
                clientCriterias.isK(),
                clientCriterias.isAddProd(),
                clientCriterias.isS(),
                clientCriterias.isM(),

                clientCriterias.isMinSize(),
                clientCriterias.isTmaProd(),
                clientCriterias.isPrice(),
                clientCriterias.isFill80(),
                clientCriterias.isPlace(),
                clientCriterias.getComment(),

                clientCriterias.getClientId(),
                Date.valueOf(clientCriterias.getDateFrom()),
                Date.valueOf(clientCriterias.getDateTo()));
    }

    @Override
    public DmpClientCriterias find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<DmpClientCriterias> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(DmpClientCriterias dmpClientCriterias) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(DmpClientCriterias dmpClientCriterias) {
        jdbcTemplate.update(SQL_REMOVE, dmpClientCriterias.getClientId(),
                Date.valueOf(dmpClientCriterias.getDateFrom()),
                Date.valueOf(dmpClientCriterias.getDateTo()));
    }

    @Override
    public List<DmpClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, dmpClientCriteriasRowMapper,
                clientId,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
