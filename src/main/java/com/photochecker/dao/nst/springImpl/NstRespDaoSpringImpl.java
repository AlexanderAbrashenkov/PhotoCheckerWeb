package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.common.UserDao;
import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstFormat;
import com.photochecker.model.nst.NstObl;
import com.photochecker.model.nst.NstResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NstRespDaoSpringImpl implements NstRespDao{

    private final String SQL_FIND_ALL = "SELECT * FROM `nst_resp`";

    private final String SQL_UPDATE = "UPDATE `nst_resp` " +
            "SET user_id = ? " +
            "WHERE format_id = ? " +
            "AND obl_id = ?";

    private final String SQL_FIND_BY_USER = "SELECT DISTINCT res.* " +
            "FROM `nst_resp` res " +
            "WHERE res.`user_id` = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private NstFormatDao nstFormatDao;
    @Autowired
    private NstOblDao nstOblDao;
    @Autowired
    private UserDao userDao;

    private List<NstFormat> nstFormatList;
    private List<NstObl> nstOblList;
    private List<User> userList;

    @Autowired
    public NstRespDaoSpringImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nst_resp")
                .usingGeneratedKeyColumns("id");
    }

    private void setNstRespFields() {
        nstFormatList = nstFormatDao.findAll();
        nstOblList = nstOblDao.findAll();
        userList = userDao.findAll();
    }

    private RowMapper<NstResp> nstRespRowMapper = (rs, rowNum) -> {
        int formatId = rs.getInt("format_id");
        int oblId = rs.getInt("obl_id");
        int userId = rs.getInt("user_id");

        NstFormat nstFormat = nstFormatList.stream()
                .filter(nstFormat1 -> nstFormat1.getId() == formatId)
                .findFirst()
                .get();

        NstObl nstObl = nstOblList.stream()
                .filter(nstObl1 -> nstObl1.getId() == oblId)
                .findFirst()
                .get();

        User user = null;
        if (userId > 0) {
            user = userList.stream()
                    .filter(user1 -> user1.getId() == userId)
                    .findFirst()
                    .get();
        }

        return new NstResp(nstFormat, nstObl, user);
    };

    @Override
    public int save(NstResp nstResp) {
        Map<String, Object> params = new HashMap<>();
        params.put("format_id", nstResp.getNstFormat().getId());
        params.put("obl_id", nstResp.getNstObl().getId());

        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public NstResp find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstResp> findAll() {
        setNstRespFields();
        return jdbcTemplate.query(SQL_FIND_ALL, nstRespRowMapper);
    }

    @Override
    public boolean update(NstResp nstResp) {
        jdbcTemplate.update(SQL_UPDATE,
                nstResp.getUser().getId(),
                nstResp.getNstFormat().getId(),
                nstResp.getNstObl().getId());
        return true;
    }

    @Override
    public void remove(NstResp nstResp) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstResp> findAllByUser(User user) {
        setNstRespFields();
        return jdbcTemplate.query(SQL_FIND_BY_USER, nstRespRowMapper,
                user.getId());
    }
}
