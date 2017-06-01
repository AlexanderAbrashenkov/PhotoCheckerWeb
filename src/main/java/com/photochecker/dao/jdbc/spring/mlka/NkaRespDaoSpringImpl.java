package com.photochecker.dao.jdbc.spring.mlka;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.dao.mlka.NkaTypeDao;
import com.photochecker.model.Distr;
import com.photochecker.model.User;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.model.mlka.NkaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 01.06.2017.
 */
public class NkaRespDaoSpringImpl implements NkaRespDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private List<NkaType> nkaTypeList;
    private List<Distr> distrList;
    private List<User> userList;

    @Autowired
    private NkaTypeDao nkaTypeDao;
    @Autowired
    private DistrDao distrDao;
    @Autowired
    private UserDao userDao;

    private final String SQL_FIND_ALL = "SELECT * FROM `nka_resp_db`";

    private final String SQL_UPDATE = "UPDATE `nka_resp_db`\n" +
            "SET `resp_user` = ?\n" +
            "WHERE `nka_type` = ?\n" +
            "AND `distr_id` = ?";

    private final String SQL_FIND_BY_PARAMS = "SELECT DISTINCT res.`nka_type`, res.`distr_id`, res.`resp_user`\n" +
            "FROM `nka_resp_db` res\n" +
            "WHERE\n" +
            "res.`resp_user` = ?";

    @Autowired
    public NkaRespDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nka_resp_db")
                .usingGeneratedKeyColumns("id");
    }

    private void setNkaTypeFields() {
        nkaTypeList = nkaTypeDao.findAll();
        distrList = distrDao.findAll();
        userList = userDao.findAll();
    }

    private RowMapper<NkaResp> nkaRespRowMapper = (rs, rowNum) -> {
        int nkaTypeId = rs.getInt("nka_type");
        int distrId = rs.getInt("distr_id");
        int userId = rs.getInt("resp_user");

        NkaType nkaType = nkaTypeList.stream()
                .filter(nkaType1 -> nkaType1.getId() == nkaTypeId)
                .findFirst()
                .get();

        Distr distr = distrList.stream()
                .filter(distr1 -> distr1.getId() == distrId)
                .findFirst()
                .get();

        User user = null;
        if (userId > 0) {
            user = userList.stream()
                    .filter(user1 -> user1.getId() == userId)
                    .findFirst()
                    .get();
        }

        return new NkaResp(
                nkaType,
                distr,
                user
        );
    };

    @Override
    public int save(NkaResp nkaResp) {
        Map<String, Object> params = new HashMap<>();
        params.put("nka_type", nkaResp.getNkaType().getId());
        params.put("distr_id", nkaResp.getDistr().getId());
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public NkaResp find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NkaResp> findAll() {
        setNkaTypeFields();
        return jdbcTemplate.query(SQL_FIND_ALL, nkaRespRowMapper);
    }

    @Override
    public boolean update(NkaResp nkaResp) {
        jdbcTemplate.update(SQL_UPDATE,
                nkaResp.getUser().getId(),
                nkaResp.getNkaType().getId(),
                nkaResp.getDistr().getId());
        return true;
    }

    @Override
    public void remove(NkaResp nkaResp) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NkaResp> findAllByUser(User user) {
        setNkaTypeFields();
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, nkaRespRowMapper,
                user.getId());
    }
}
