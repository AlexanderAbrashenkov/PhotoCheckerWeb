package com.photochecker.dao.common.springImpl;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.common.Distr;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by market6 on 18.05.2017.
 */
public class ResponsibilityDaoSpringImpl implements ResponsibilityDao {

    private JdbcTemplate jdbcTemplate;
    private List<ReportType> reportTypeList;
    private List<Distr> distrList;
    private List<User> userList;

    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private DistrDao distrDao;
    @Autowired
    private UserDao userDao;

    private final String SQL_FIND_ALL = "SELECT * FROM `responsibility_db`";

    private final String SQL_UPDATE = "UPDATE `responsibility_db`\n" +
            "SET `resp_user` = ?\n" +
            "WHERE `report_type` = ?\n" +
            "AND `distr_id` = ?";

    private final String SQL_FIND_BY_PARAMS = "SELECT DISTINCT res.`report_type`, res.`distr_id`, res.`resp_user`\n" +
            "FROM `responsibility_db` res\n" +
            "WHERE\n" +
            "res.`resp_user` = ?";

    private final String SQL_SAVE = "INSERT INTO `responsibility_db` " +
            "(`report_type`, `distr_id`) VALUES (?, ?);";

    @Autowired
    public ResponsibilityDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void setResponsibilityFields() {
        reportTypeList = reportTypeDao.findAll();
        distrList = distrDao.findAll();
        userList = userDao.findAll();
    }

    private RowMapper<Responsibility> responsibilityRowMapper = (resultSet, i) -> {
        int repType = resultSet.getInt("report_type");
        int distrId = resultSet.getInt("distr_id");
        int userId = resultSet.getInt("resp_user");

        ReportType reportType = reportTypeList.stream()
                .filter(reportType1 -> reportType1.getId() == repType)
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

        return new Responsibility(
                reportType,
                distr,
                user
        );
    };

    @Override
    public int save(Responsibility responsibility) {
        return jdbcTemplate.update(SQL_SAVE,
                responsibility.getReportType().getId(),
                responsibility.getDistr().getId());
    }

    @Override
    public Responsibility find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<Responsibility> findAll() {
        setResponsibilityFields();
        return jdbcTemplate.query(SQL_FIND_ALL, responsibilityRowMapper);
    }

    @Override
    public boolean update(Responsibility responsibility) {
        return jdbcTemplate.execute(SQL_UPDATE, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setInt(1, responsibility.getUser().getId());
                preparedStatement.setInt(2, responsibility.getReportType().getId());
                preparedStatement.setInt(3, responsibility.getDistr().getId());
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public void remove(Responsibility responsibility) {
        throw new RuntimeException("This methos not used");
    }

    @Override
    public List<Responsibility> findAllByUser(User user) {
        setResponsibilityFields();
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, responsibilityRowMapper,
                user.getId());
    }
}
