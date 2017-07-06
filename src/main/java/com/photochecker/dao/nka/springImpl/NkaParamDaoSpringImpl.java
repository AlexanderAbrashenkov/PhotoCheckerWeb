package com.photochecker.dao.nka.springImpl;

import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.nka.NkaParamDao;
import com.photochecker.model.nka.NkaParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class NkaParamDaoSpringImpl implements NkaParamDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_SAVE = "INSERT INTO `nka_param`\n" +
            "(`nka_id`, `mz_dp_short`, `mz_dp_full`, `mz_bb_short`, `mz_bb_full`, `mz_mr_short`, `mz_mr_full`, " +
            "`k_dp_short`, `k_dp_full`, `k_bb_short`, `k_bb_full`, `k_mr_short`, `k_mr_full`, " +
            "`s_dp_short`, `s_dp_full`, `s_bb_short`, `s_bb_full`, `s_mr_short`, `s_mr_full`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?)";

    private final String SQL_FIND_BY_ID = "SELECT * FROM `nka_param`\n" +
            "WHERE `nka_id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `nka_param`";

    private final String SQL_UPDATE = "UPDATE `nka_param` SET\n" +
            "`mz_dp_short` = ?, `mz_dp_full` = ?, `mz_bb_short` = ?, `mz_bb_full` = ?, `mz_mr_short` = ?, `mz_mr_full` = ?, " +
            "`k_dp_short` = ?, `k_dp_full` = ?, `k_bb_short` = ?, `k_bb_full` = ?, `k_mr_short` = ?, `k_mr_full` = ?, " +
            "`s_dp_short` = ?, `s_dp_full` = ?, `s_bb_short` = ?, `s_bb_full` = ?, `s_mr_short` = ?, `s_mr_full` = ? " +
            "WHERE `nka_id` = ?";

    private final String SQL_REMOVE = "DELETE FROM `nka_param`\n" +
            "WHERE `nka_id` = ?";

    @Autowired
    private LkaDao lkaDao;

    @Autowired
    public NkaParamDaoSpringImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NkaParam> nkaParamRowMapper = (resultSet, i) -> {

        return new NkaParam(
                resultSet.getInt("nka_id"),
                lkaDao.find(resultSet.getInt("nka_id")).getName(),
                resultSet.getString("mz_dp_short"),
                resultSet.getString("mz_dp_full"),
                resultSet.getString("mz_bb_short"),
                resultSet.getString("mz_bb_full"),
                resultSet.getString("mz_mr_short"),
                resultSet.getString("mz_mr_full"),

                resultSet.getString("k_dp_short"),
                resultSet.getString("k_dp_full"),
                resultSet.getString("k_bb_short"),
                resultSet.getString("k_bb_full"),
                resultSet.getString("k_mr_short"),
                resultSet.getString("k_mr_full"),

                resultSet.getString("s_dp_short"),
                resultSet.getString("s_dp_full"),
                resultSet.getString("s_bb_short"),
                resultSet.getString("s_bb_full"),
                resultSet.getString("s_mr_short"),
                resultSet.getString("s_mr_full"));
    };

    @Override
    public int save(NkaParam nkaParam) {
        return jdbcTemplate.update(SQL_SAVE,
                nkaParam.getNkaId(),
                nkaParam.getMzDpShort(),
                nkaParam.getMzDpFull(),
                nkaParam.getMzBbShort(),
                nkaParam.getMzBbFull(),
                nkaParam.getMzMrShort(),
                nkaParam.getMzMrFull(),

                nkaParam.getkDpShort(),
                nkaParam.getkDpFull(),
                nkaParam.getkBbShort(),
                nkaParam.getkBbFull(),
                nkaParam.getkMrShort(),
                nkaParam.getkMrFull(),

                nkaParam.getsDpShort(),
                nkaParam.getsDpFull(),
                nkaParam.getsBbShort(),
                nkaParam.getsBbFull(),
                nkaParam.getsMrShort(),
                nkaParam.getsMrFull()
                );
    }

    @Override
    public NkaParam find(int id) {
        List<NkaParam> result = jdbcTemplate.query(SQL_FIND_BY_ID, nkaParamRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NkaParam> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, nkaParamRowMapper);
    }

    @Override
    public boolean update(NkaParam nkaParam) {
        jdbcTemplate.update(SQL_UPDATE,
                nkaParam.getMzDpShort(),
                nkaParam.getMzDpFull(),
                nkaParam.getMzBbShort(),
                nkaParam.getMzBbFull(),
                nkaParam.getMzMrShort(),
                nkaParam.getMzMrFull(),

                nkaParam.getkDpShort(),
                nkaParam.getkDpFull(),
                nkaParam.getkBbShort(),
                nkaParam.getkBbFull(),
                nkaParam.getkMrShort(),
                nkaParam.getkMrFull(),

                nkaParam.getsDpShort(),
                nkaParam.getsDpFull(),
                nkaParam.getsBbShort(),
                nkaParam.getsBbFull(),
                nkaParam.getsMrShort(),
                nkaParam.getsMrFull(),

                nkaParam.getNkaId()
                );
        return true;
    }

    @Override
    public void remove(NkaParam nkaParam) {
        jdbcTemplate.update(SQL_REMOVE, nkaParam.getNkaId());
    }
}
