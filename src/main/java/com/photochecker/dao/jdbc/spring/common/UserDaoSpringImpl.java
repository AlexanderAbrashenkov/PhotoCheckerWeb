package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 18.05.2017.
 */
public class UserDaoSpringImpl implements UserDao {

    private final String SQL_FIND_BY_ID = "SELECT * FROM `users`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `users`";

    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `users` " +
            "WHERE `user_login` = ? ";

    private final String SQL_SAVE_USER_REPORTS = "INSERT INTO `report_type_user` (`user_id`, `report_type`)\n" +
            "VALUES (?, ?)";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private ReportTypeDao reportTypeDao;

    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("user_login");
        String userName = resultSet.getString("user_name");

        int userRole = resultSet.getInt("user_role");

        User user = new User(id, login, userName, userRole, null);

        List<ReportType> reportTypeList = reportTypeDao.findAllByUser(user);

        user.setReportTypeList(reportTypeList);

        return user;
    };

    @Autowired
    public UserDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("users")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public int save(User user) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public User find(int id) {
        List<User> result = jdbcTemplate.query(SQL_FIND_BY_ID, userRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public boolean update(User user) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(User user) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<User> findAllByLogin(String login) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, userRowMapper, login);
    }

    @Override
    public User checkLoginAndPassword(String login, String password) {

        Map<String, Object> userRow = jdbcTemplate.queryForMap(SQL_FIND_BY_PARAMS, login);
        User user = findAllByLogin(login).get(0);

        int id = (int) userRow.get("id");
        String userName = (String) userRow.get("user_name");
        String userPass = (String) userRow.get("user_pass");
        String userSalt = (String) userRow.get("salt");

        String passSalt = password + userSalt;
        StringBuffer code = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte bytes[] = passSalt.getBytes();
            byte digest[] = messageDigest.digest(bytes); //save code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!code.toString().equals(userPass)) {
            return null;
        }

        return user;
    }

    @Override
    public int saveNewUser(User user, String password, String salt) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", user.getUserLogin());
        params.put("user_pass", password);
        params.put("user_name", user.getUserName());
        params.put("user_role", user.getRole());
        params.put("salt", salt);
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return key.intValue();
    }

    @Override
    public void saveUserReports(User user) {
        for (ReportType reportType : user.getReportTypeList()) {
            jdbcTemplate.update(SQL_SAVE_USER_REPORTS,
                    user.getId(),
                    reportType.getId());
        }
    }
}
