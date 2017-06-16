package com.photochecker.dao.lka.springImpl;

import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.model.common.Lka;
import com.photochecker.model.lka.LkaCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by 777 on 21.05.2017.
 */
public class LkaCriteriasDaoSpringImpl implements LkaCriteriasDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LkaDao lkaDao;

    private List<Lka> lkaList;

    private final String SQL_SAVE = "INSERT INTO `lka_criterias_db`\n" +
            "(`lka_id`, `crit1_name`, `crit1_mz`, `crit1_k`, `crit1_s`, `crit1_m`, `crit2_name`)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_FIND_BY_ID = "SELECT * FROM `lka_criterias_db`\n" +
            "WHERE `lka_id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `lka_criterias_db`";

    private final String SQL_UPDATE = "UPDATE `lka_criterias_db` SET\n" +
            "crit1_name = ?, crit1_mz = ?, crit1_k = ?, crit1_s = ?, crit1_m = ?, crit2_name = ?\n" +
            "WHERE lka_id = ?";

    private final String SQL_REMOVE = "DELETE FROM `lka_criterias_db`\n" +
            "WHERE `lka_id` = ?";

    @Autowired
    public LkaCriteriasDaoSpringImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void serLkaCriteriasFields() {
        if (lkaList == null) {
            lkaList = lkaDao.findAll();
        }
    }

    private RowMapper<LkaCriterias> lkaCriteriasRowMapper = (resultSet, i) -> {
        int lkaId = resultSet.getInt("lka_id");
        Lka lka;
        try {
            lka = lkaList.stream()
                    .filter(lka1 -> lka1.getId() == lkaId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            lka = new Lka(lkaId, "");
        }
        return new LkaCriterias(
                lka,
                resultSet.getString("crit1_name"),
                resultSet.getInt("crit1_mz"),
                resultSet.getInt("crit1_k"),
                resultSet.getInt("crit1_s"),
                resultSet.getInt("crit1_m"),
                resultSet.getString("crit2_name"));
    };

    @Override
    public int save(LkaCriterias lkaCriterias) {
        return jdbcTemplate.execute(SQL_SAVE, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                statement.setInt(1, lkaCriterias.getLka().getId());
                statement.setString(2, lkaCriterias.getCrit1Name());
                statement.setInt(3, lkaCriterias.getCrit1Mz());
                statement.setInt(4, lkaCriterias.getCrit1K());
                statement.setInt(5, lkaCriterias.getCrit1S());
                statement.setInt(6, lkaCriterias.getCrit1M());
                statement.setString(7, lkaCriterias.getCrit2Name());
                return statement.executeUpdate();
            }
        });
    }

    @Override
    public LkaCriterias find(int id) {
        serLkaCriteriasFields();
        List<LkaCriterias> result = jdbcTemplate.query(SQL_FIND_BY_ID, lkaCriteriasRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<LkaCriterias> findAll() {
        serLkaCriteriasFields();
        return jdbcTemplate.query(SQL_FIND_ALL, lkaCriteriasRowMapper);
    }

    @Override
    public boolean update(LkaCriterias lkaCriterias) {
        return jdbcTemplate.execute(SQL_UPDATE, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                statement.setString(1, lkaCriterias.getCrit1Name());
                statement.setInt(2, lkaCriterias.getCrit1Mz());
                statement.setInt(3, lkaCriterias.getCrit1K());
                statement.setInt(4, lkaCriterias.getCrit1S());
                statement.setInt(5, lkaCriterias.getCrit1M());
                statement.setString(6, lkaCriterias.getCrit2Name());
                statement.setInt(7, lkaCriterias.getLka().getId());
                return statement.execute();
            }
        });
    }

    @Override
    public void remove(LkaCriterias lkaCriterias) {
        jdbcTemplate.execute(SQL_REMOVE, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                statement.setInt(1, lkaCriterias.getLka().getId());
                return statement.execute();
            }
        });
    }
}
