package com.photochecker.dao.lka.springImpl;

import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.model.lka.ClientCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by 777 on 18.05.2017.
 */
public class ClientCriteriasDaoSpringImpl implements ClientCriteriasDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_SAVE = "INSERT INTO `save_lka_db`\n" +
            "(`save_date`, " +
            "`has_mz`, `has_photo_mz`, `is_correct_mz`, `has_add_prod_mz`, `crit1_mz`, `crit2_mz`, " +
            "`has_k`, `has_photo_k`, `is_correct_k`, `crit1_k`, `crit2_k`, " +
            "`has_s`, `has_photo_s`, `is_correct_s`, `crit1_s`, `crit2_s`, " +
            "`has_m`, `has_photo_m`, `is_correct_m`, `crit1_m`, `crit2_m`, " +
            "`oos`, `comm`, " +
            "`client_id`, `date_from`, `date_to`)\n" +
            "VALUES (?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "?, ?," +
            "?, ?, ?);";

    private final String SQL_UPDATE = "UPDATE `save_lka_db` SET\n" +
            "`save_date` = ?, " +
            "`has_mz` = ?, `has_photo_mz` = ?, `is_correct_mz` = ?, `has_add_prod_mz` = ?, `crit1_mz` = ?, `crit2_mz` = ?, " +
            "`has_k` = ?, `has_photo_k` = ?, `is_correct_k` = ?, `crit1_k` = ?, `crit2_k` = ?, " +
            "`has_s` = ?, `has_photo_s` = ?, `is_correct_s` = ?, `crit1_s` = ?, `crit2_s` = ?, " +
            "`has_m` = ?, `has_photo_m` = ?, `is_correct_m` = ?, `crit1_m` = ?, `crit2_m` = ?, " +
            "`oos` = ?, `comm` = ? " +
            "WHERE `client_id` = ?\n " +
            "AND `date_from` = ?\n " +
            "AND `date_to` = ?";

    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `save_lka_db`\n" +
            "WHERE `client_id` = ?\n" +
            "AND `date_from` = ?\n" +
            "AND `date_to` = ?";

    @Autowired
    public ClientCriteriasDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ClientCriterias> clientCriteriasRowMapper = (resultSet, i) -> {
        return new ClientCriterias(
                resultSet.getInt("client_id"),
                resultSet.getDate("date_from").toLocalDate(),
                resultSet.getDate("date_to").toLocalDate(),
                resultSet.getTimestamp("save_date").toLocalDateTime(),

                resultSet.getBoolean("has_mz"),
                resultSet.getBoolean("has_photo_mz"),
                resultSet.getBoolean("is_correct_mz"),
                resultSet.getBoolean("has_add_prod_mz"),
                resultSet.getBoolean("crit1_mz"),
                resultSet.getBoolean("crit2_mz"),

                resultSet.getBoolean("has_k"),
                resultSet.getBoolean("has_photo_k"),
                resultSet.getBoolean("is_correct_k"),
                resultSet.getBoolean("crit1_k"),
                resultSet.getBoolean("crit2_k"),

                resultSet.getBoolean("has_s"),
                resultSet.getBoolean("has_photo_s"),
                resultSet.getBoolean("is_correct_s"),
                resultSet.getBoolean("crit1_s"),
                resultSet.getBoolean("crit2_s"),

                resultSet.getBoolean("has_m"),
                resultSet.getBoolean("has_photo_m"),
                resultSet.getBoolean("is_correct_m"),
                resultSet.getBoolean("crit1_m"),
                resultSet.getBoolean("crit2_m"),

                resultSet.getBoolean("oos"),
                resultSet.getString("comm")
        );
    };

    //todo: return id of saved row
    @Override
    public int save(ClientCriterias clientCriterias) {
        return jdbcTemplate.execute(SQL_SAVE, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                fillStatement(preparedStatement, clientCriterias);
                return preparedStatement.executeUpdate();
            }
        });
    }

    @Override
    public ClientCriterias find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<ClientCriterias> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(ClientCriterias clientCriterias) {
        return jdbcTemplate.execute(SQL_UPDATE, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                fillStatement(preparedStatement, clientCriterias);
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public void remove(ClientCriterias clientCriterias) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<ClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, clientCriteriasRowMapper,
                clientId,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }

    private void fillStatement(PreparedStatement statement, ClientCriterias clientCriterias) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(clientCriterias.getSaveDate()));

        statement.setBoolean(2, clientCriterias.isHasMz());
        statement.setBoolean(3, clientCriterias.isHasPhotoMz());
        statement.setBoolean(4, clientCriterias.isCorrectMz());
        statement.setBoolean(5, clientCriterias.isHasAddProdMz());
        statement.setBoolean(6, clientCriterias.isCrit1Mz());
        statement.setBoolean(7, clientCriterias.isCrit2Mz());

        statement.setBoolean(8, clientCriterias.isHasK());
        statement.setBoolean(9, clientCriterias.isHasPhotoK());
        statement.setBoolean(10, clientCriterias.isCorrectK());
        statement.setBoolean(11, clientCriterias.isCrit1K());
        statement.setBoolean(12, clientCriterias.isCrit2K());

        statement.setBoolean(13, clientCriterias.isHasS());
        statement.setBoolean(14, clientCriterias.isHasPhotoS());
        statement.setBoolean(15, clientCriterias.isCorrectS());
        statement.setBoolean(16, clientCriterias.isCrit1S());
        statement.setBoolean(17, clientCriterias.isCrit2S());

        statement.setBoolean(18, clientCriterias.isHasM());
        statement.setBoolean(19, clientCriterias.isHasPhotoM());
        statement.setBoolean(20, clientCriterias.isCorrectM());
        statement.setBoolean(21, clientCriterias.isCrit1M());
        statement.setBoolean(22, clientCriterias.isCrit2M());

        statement.setBoolean(23, clientCriterias.isOos());
        statement.setString(24, clientCriterias.getComment());

        statement.setInt(25, clientCriterias.getClientId());
        statement.setDate(26, Date.valueOf(clientCriterias.getDateFrom()));
        statement.setDate(27, Date.valueOf(clientCriterias.getDateTo()));
    }
}
